package dev.kameleon.data;

import java.util.List;

public class CamelType {
    private String name;
    private List<CamelVersion> versions;

    public CamelType() {
    }

    public CamelType(String name, List<CamelVersion> versions) {
        this.name = name;
        this.versions = versions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CamelVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<CamelVersion> versions) {
        this.versions = versions;
    }
}
