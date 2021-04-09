package dev.kameleon;

import dev.kameleon.version.VersionService;
import io.vertx.mutiny.core.Vertx;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.shared.invoker.*;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class GeneratorService {

    @Inject
    VersionService versionService;

    static final String MAVEN_REPO =  System.getProperty("java.io.tmpdir")+"/maven";

    public String generate(String type, String archetypeVersion,
                    String groupId, String artifactId, String version, String components) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String folder = Vertx.vertx().fileSystem().createTempDirectoryBlocking(uuid);
        File temp = new File(folder);
        String zipFileName = temp.getAbsolutePath() + "/" + artifactId + ".zip";
        if (!"quarkus".equals(type)) {
            String folderName = temp.getAbsolutePath() + "/" + artifactId;
            generateClassicArchetype(temp, type, archetypeVersion, groupId, artifactId, version);
            if (Files.exists(Paths.get(folderName)) && !components.isBlank() && !components.isEmpty()) {
                addComponents(folderName, components);
                packageProject(folderName, zipFileName);
            } else if (Files.exists(Paths.get(folderName))) {
                packageProject(folderName, zipFileName);
            }
        } else {
            String quarkusVersion = versionService.getQuarkusVersion(archetypeVersion);
            generateQuarkusArchetype(temp, quarkusVersion, groupId, artifactId, version, components);
            String folderName = temp.getAbsolutePath() + "/code-with-quarkus";
            packageProject(folderName, zipFileName);
        }
        return zipFileName;
    }

    private void addComponents(String folderName, String components) throws Exception {
        File pom = new File(folderName, "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pom));
        List<Dependency> dependencies = model.getDependencies();
        List<Dependency> additional = Arrays.stream(components.split(",")).map(s -> {
            Dependency dep = new Dependency();
            dep.setArtifactId(s);
            dep.setGroupId("org.apache.camel");
            return dep;
        }).collect(Collectors.toList());
        dependencies.addAll(additional);
        model.setDependencies(dependencies);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileWriter(pom), model);
    }

    private void generateClassicArchetype(File folder, String type, String archetypeVersion,
                                  String groupId, String artifactId, String version)
            throws MavenInvocationException, IOException {
        Properties properties = new Properties();
        properties.setProperty("groupId", groupId);
        properties.setProperty("package", groupId + "." + artifactId);
        properties.setProperty("artifactId", artifactId);
        properties.setProperty("version", version);
        properties.setProperty("archetypeVersion", archetypeVersion);
        properties.setProperty("archetypeGroupId", ConfigProvider.getConfig().getValue("camel.archetype.group." + type, String.class));
        properties.setProperty("archetypeArtifactId",
                archetypeVersion.startsWith("1") || archetypeVersion.startsWith("2")
                ? ConfigProvider.getConfig().getValue("camel.archetype.artifact." + type +".legacy", String.class)
                : ConfigProvider.getConfig().getValue("camel.archetype.artifact." + type, String.class)
        );

        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList("archetype:generate"));
        request.setBatchMode(true);
        request.setProperties(properties);
        request.setBaseDirectory(folder);

        execute(request);
    }

    private void generateQuarkusArchetype(File folder, String quarkusVersion, String groupId, String artifactId, String version, String components)
            throws MavenInvocationException, IOException {
        Properties properties = new Properties();
        properties.setProperty("groupId", groupId);
        properties.setProperty("package", groupId + "." + artifactId);
        properties.setProperty("artifactId", artifactId);
        properties.setProperty("version", version);
        properties.setProperty("extensions", components);

        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList("io.quarkus:quarkus-maven-plugin:"+quarkusVersion+":create "));
        request.setBatchMode(true);
        request.setProperties(properties);
        request.setBaseDirectory(folder);

        execute(request);
    }

    private void execute(InvocationRequest request)throws MavenInvocationException, IOException {
        Path localRepo = Files.exists(Paths.get(MAVEN_REPO)) ? Paths.get(MAVEN_REPO) : Files.createDirectory(Paths.get(MAVEN_REPO));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
        invoker.setLocalRepositoryDirectory(localRepo.toFile());
        invoker.execute(request);
    }

    private void packageProject(String folder, String filename) {
        try (ZipArchiveOutputStream archive = new ZipArchiveOutputStream(new FileOutputStream(filename))) {
            File folderToZip = new File(folder);
            Files.walk(folderToZip.toPath()).forEach(p -> {
                File file = p.toFile();
                if (!file.isDirectory()) {
                    ZipArchiveEntry entry_1 = new ZipArchiveEntry(file, file.toString().replace(folder, ""));
                    try (FileInputStream fis = new FileInputStream(file)) {
                        archive.putArchiveEntry(entry_1);
                        IOUtils.copy(fis, archive);
                        archive.closeArchiveEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            archive.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}