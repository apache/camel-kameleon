package dev.kameleon.model;

import java.util.List;

public class KameletComponent extends AbstractComponent {
    private String group;
    private String icon;

    public KameletComponent(String name, String title, String description, String supportLevel, List<String> labels, String group, String icon) {
        super(name, title, description, supportLevel, labels);
        this.group = group;
        this.icon = icon;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
