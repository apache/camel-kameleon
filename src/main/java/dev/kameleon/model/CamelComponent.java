package dev.kameleon.model;

import java.util.List;

public class CamelComponent extends AbstractComponent {
    private String firstVersion;
    private String artifactId;
    private Boolean deprecated;
    private Boolean nativeSupported;

    public CamelComponent() {
    }

    public CamelComponent(String name, String title, String description, String supportLevel, List<String> labels, String firstVersion, String artifactId, Boolean deprecated, Boolean nativeSupported) {
        super(name, title, description, supportLevel, labels);
        this.firstVersion = firstVersion;
        this.artifactId = artifactId;
        this.deprecated = deprecated;
        this.nativeSupported = nativeSupported;
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

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Boolean getNativeSupported() {
        return nativeSupported;
    }

    public void setNativeSupported(Boolean nativeSupported) {
        this.nativeSupported = nativeSupported;
    }
}
