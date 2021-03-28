package one.entropy.kameleon;

import io.vertx.core.Vertx;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.maven.shared.invoker.*;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@ApplicationScoped
public class GeneratorService {

    String generate(String type, String archetypeVersion,
                    String groupId, String artifactId, String version, String components) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String folder = Vertx.vertx().fileSystem().createTempDirectoryBlocking(uuid);
        File temp = new File(folder);
        String folderName = temp.getAbsolutePath() + "/" + artifactId;
        String zipFileName = temp.getAbsolutePath() + "/" + artifactId + ".zip";
        generateArchetype(temp, type, archetypeVersion, groupId, artifactId, version, components);
//        if (Files.exists(Paths.get(folderName))) {
            packageProject(folderName, zipFileName);
//        }
        return zipFileName;
    }

    InvocationResult generateArchetype(File folder, String type, String archetypeVersion,
                                       String groupId, String artifactId, String version,
                                       String components) throws MavenInvocationException {
        Properties properties = new Properties();
        properties.setProperty("groupId", groupId);
        properties.setProperty("package", groupId + "." + artifactId);
        properties.setProperty("artifactId", artifactId);
        properties.setProperty("version", version);
        properties.setProperty("archetypeVersion", archetypeVersion);
        properties.setProperty("archetypeGroupId", "org.apache.camel.archetypes");
        properties.setProperty("archetypeArtifactId", ConfigProvider.getConfig().getValue("camel.archetype." + type, String.class));


        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList("archetype:generate"));
        request.setBatchMode(true);
        request.setProperties(properties);
        request.setBaseDirectory(folder);
        Invoker invoker = new DefaultInvoker();
        return invoker.execute(request);
    }

    void packageProject(String folder, String filename) {
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