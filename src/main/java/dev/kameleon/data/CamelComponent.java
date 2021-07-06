package dev.kameleon.data;

import java.util.List;

public class CamelComponent {
    private String component;
    private String name;
    private String description;
    private String supportLevel;
    private String firstVersion;
    private String artifactId;
    private Boolean deprecated;
    private Boolean nativeSupported;
    private List<String> labels;

    public CamelComponent() {
    }

    public CamelComponent(String component, String name, String description, String supportLevel, String firstVersion,
                          String artifactId, Boolean deprecated, Boolean nativeSupported, List<String> labels) {
        this.component = component;
        this.name = name;
        this.description = description;
        this.labels = labels;
        this.supportLevel = supportLevel;
        this.firstVersion = firstVersion;
        this.artifactId = artifactId;
        this.deprecated = deprecated;
        this.nativeSupported = nativeSupported;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(String supportLevel) {
        this.supportLevel = supportLevel;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getFirstVersion() {
        return firstVersion;
    }

    public void setFirstVersion(String firstVersion) {
        this.firstVersion = firstVersion;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public Boolean getNativeSupported() {
        return nativeSupported;
    }

    public void setNativeSupported(Boolean nativeSupported) {
        this.nativeSupported = nativeSupported;
    }
}
