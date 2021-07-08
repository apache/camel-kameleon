package dev.kameleon.model;

import dev.kameleon.model.CamelType;

import java.util.List;

public class KameleonConfiguration {
    private List<CamelType> types;

    public KameleonConfiguration() {
    }

    public List<CamelType> getTypes() {
        return types;
    }

    public void setTypes(List<CamelType> types) {
        this.types = types;
    }
}
