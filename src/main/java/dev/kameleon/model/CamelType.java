package dev.kameleon.model;

import java.util.List;

public class CamelType {
    private String name;
    private String pageTitle;
    private String componentListTitle;
    private List<CamelVersion> versions;

    public CamelType() {
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

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getComponentListTitle() {
        return componentListTitle;
    }

    public void setComponentListTitle(String componentListTitle) {
        this.componentListTitle = componentListTitle;
    }
}
