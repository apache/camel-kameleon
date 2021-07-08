package dev.kameleon.model;

import java.util.List;

public abstract class AbstractComponent {
    protected String name;
    protected String title;
    protected String description;
    protected String supportLevel;
    protected List<String> labels;

    public AbstractComponent() {
    }

    public AbstractComponent(String name, String title, String description, String supportLevel, List<String> labels) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.supportLevel = supportLevel;
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(String supportLevel) {
        this.supportLevel = supportLevel;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
