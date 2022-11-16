/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.kameleon.generator;

import io.vertx.mutiny.core.Vertx;

import org.apache.camel.kameleon.config.ConfigurationResource;
import org.apache.camel.kameleon.model.CamelType;
import org.apache.camel.kameleon.model.CamelVersion;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.shared.invoker.*;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectGeneratorService {

    private static final String CAMEL_QUARKUS_CORE = "camel-quarkus-core";

    @Inject
    Vertx vertx;

    @Inject
    ConfigurationResource configurationResource;

    static final String MAVEN_REPO = System.getProperty("java.io.tmpdir") + "/maven";

    public String generate(String type, String archetypeVersion,
                           String groupId, String artifactId, String version, String javaVersion, String components) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String folder = vertx.fileSystem().createTempDirectoryBlocking(uuid);
        File temp = new File(folder);
        String zipFileName = temp.getAbsolutePath() + "/" + artifactId + ".zip";
        if (!"quarkus".equals(type)) {
            String folderName = temp.getAbsolutePath() + "/" + artifactId;
            generateClassicArchetype(temp, type, archetypeVersion, groupId, artifactId, version);
            Path path = Paths.get(folderName);
            if (Files.exists(path) && !components.isBlank() && !components.isEmpty()) {
                addComponents(folderName, type, components);
                setJavaVersion(folderName, javaVersion);
                packageProject(folderName, zipFileName);
            } else if (Files.exists(path)) {
                setJavaVersion(folderName, javaVersion);
                packageProject(folderName, zipFileName);
            }
        } else {
            CamelType camelType = configurationResource.getKc().getTypes().stream().filter(t -> t.getName().equals("quarkus")).findFirst().get();
            String quarkusVersion = camelType.getVersions().stream().filter(cv -> cv.getName().equals(archetypeVersion)).findFirst().get().getRuntimeVersion();
            generateQuarkusArchetype(temp, quarkusVersion, groupId, artifactId, version, components);
            String folderName = temp.getAbsolutePath() + "/code-with-quarkus";
            packageProject(folderName, zipFileName);
        }
        return zipFileName;
    }

    private void addComponents(String folderName, String type, String components) throws Exception {
        File pom = new File(folderName, "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pom));
        List<Dependency> dependencies = model.getDependencies();
        List<Dependency> additional = Arrays.stream(components.split(",")).distinct().map(s -> {
            Dependency dep = new Dependency();
            dep.setArtifactId(s);
            String gid = type.equals("spring") ? "org.apache.camel.springboot" : "org.apache.camel";
            dep.setGroupId(gid);
            return dep;
        }).collect(Collectors.toList());
        dependencies.addAll(additional);
        model.setDependencies(dependencies);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileWriter(pom), model);
    }

    private void setJavaVersion(String folderName, String javaVersion) throws Exception {
        File pom = new File(folderName, "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pom));
        List<Plugin> plugins = model.getBuild().getPlugins();
        Plugin mavenCompiler = plugins.stream().filter(p -> p.getArtifactId().equals("maven-compiler-plugin")).findFirst().get();
        Xpp3Dom config = (Xpp3Dom) mavenCompiler.getConfiguration();
        if (config.getChild("source") == null) config.addChild(new Xpp3Dom("source"));
        if (config.getChild("target") == null) config.addChild(new Xpp3Dom("target"));
        config.getChild("source").setValue(javaVersion.equals("8") ? "1.8" : javaVersion);
        config.getChild("target").setValue(javaVersion.equals("8") ? "1.8" : javaVersion);
        mavenCompiler.setConfiguration(config);

        model.getBuild().getPlugins().removeIf(p -> p.getArtifactId().equals("maven-compiler-plugin"));
        model.getBuild().getPlugins().add(mavenCompiler);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileWriter(pom), model);
    }

    private void generateClassicArchetype(File folder, String type, String archetypeVersion,
                                          String groupId, String artifactId, String version)
            throws MavenInvocationException, IOException {
        CamelType camelType = configurationResource.getKc().getTypes().stream().filter(ct -> ct.getName().equals(type)).findFirst().get();
        CamelVersion camelVersion = camelType.getVersions().stream().filter(cv -> cv.getName().equals(archetypeVersion)).findFirst().get();

        Properties properties = new Properties();
        properties.setProperty("groupId", groupId);
        properties.setProperty("package", generatePackageName(groupId, artifactId));
        properties.setProperty("artifactId", artifactId);
        properties.setProperty("version", version);
        properties.setProperty("archetypeVersion", archetypeVersion);
        properties.setProperty("archetypeGroupId", camelVersion.getArchetypeGroupId());
        properties.setProperty("archetypeArtifactId", camelVersion.getArchetypeArtifactId());

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
        properties.setProperty("package", generatePackageName(groupId, artifactId));
        properties.setProperty("artifactId", artifactId);
        properties.setProperty("version", version);
//        properties.setProperty("noExamples", "true");
        properties.setProperty("extensions",
                (components != null && !components.trim().isEmpty()) ? CAMEL_QUARKUS_CORE + "," + components : CAMEL_QUARKUS_CORE);

        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList("io.quarkus:quarkus-maven-plugin:" + quarkusVersion + ":create "));
        request.setBatchMode(true);
        request.setProperties(properties);
        request.setBaseDirectory(folder);
        execute(request);
    }

    private static String generatePackageName(String groupId, String artifactId) {
        StringBuilder cleanArtifact = new StringBuilder();
        for (Character c : artifactId.toCharArray()) {
            if (Character.isJavaIdentifierPart(c)) {
                cleanArtifact.append(c);
            }
        }
        return groupId + "." + cleanArtifact;
    }

    private void execute(InvocationRequest request) throws MavenInvocationException, IOException {
        Path path = Paths.get(MAVEN_REPO);
        Path localRepo = Files.exists(path) ? path : Files.createDirectory(path);

        Invoker invoker = new DefaultInvoker();
        String mHome = System.getenv("MAVEN_HOME");
        if (mHome == null) {
            throw new IllegalStateException("OS Environment MAVEN_HOME is not set");
        }
        invoker.setMavenHome(new File(mHome));
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
