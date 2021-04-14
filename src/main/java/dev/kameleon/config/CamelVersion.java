package dev.kameleon.config;

import java.util.List;

public class CamelVersion {
    private String name;
    private Boolean lts;
    private List<String> javaVersions;
    private String defaultJava;
    private String runtimeVersion; // ex. Quarkus version
    private String archetypeGroupId;
    private String archetypeArtifactId;

    public CamelVersion() {
    }

    public CamelVersion(String name, Boolean lts, List<String> javaVersions, String defaultJava, String runtimeVersion,
                        String archetypeGroupId, String archetypeArtifactId) {
        this.name = name;
        this.lts = lts;
        this.javaVersions = javaVersions;
        this.defaultJava = defaultJava;
        this.runtimeVersion = runtimeVersion;
        this.archetypeGroupId = archetypeGroupId;
        this.archetypeArtifactId = archetypeArtifactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLts() {
        return lts;
    }

    public void setLts(Boolean lts) {
        this.lts = lts;
    }

    public List<String> getJavaVersions() {
        return javaVersions;
    }

    public void setJavaVersions(List<String> javaVersions) {
        this.javaVersions = javaVersions;
    }

    public String getDefaultJava() {
        return defaultJava;
    }

    public void setDefaultJava(String defaultJava) {
        this.defaultJava = defaultJava;
    }

    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    public void setRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }

    public String getArchetypeGroupId() {
        return archetypeGroupId;
    }

    public void setArchetypeGroupId(String archetypeGroupId) {
        this.archetypeGroupId = archetypeGroupId;
    }

    public String getArchetypeArtifactId() {
        return archetypeArtifactId;
    }

    public void setArchetypeArtifactId(String archetypeArtifactId) {
        this.archetypeArtifactId = archetypeArtifactId;
    }
}
