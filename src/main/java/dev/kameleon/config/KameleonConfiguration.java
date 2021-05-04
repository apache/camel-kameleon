package dev.kameleon.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        KameleonConfiguration k = new KameleonConfiguration();
        k.types = List.of(
                new CamelType("Standalone", List.of(
                        new CamelVersion("3.9.0", "", List.of("1.8", "11", "14"), "11",
                                "", "org.apache.camel.archetypes", "camel-archetype-main"),
                        new CamelVersion("3.7.4", "LTS", List.of("1.8", "11", "14"), "11",
                                "", "org.apache.camel.archetypes", "camel-archetype-main")
                )),
                new CamelType("Quarkus", List.of(
                        new CamelVersion("1.8.1", "", List.of("1"), "11", "1.7.4",
                        "io.quarkus","quarkus-maven-plugin")
                ))
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("target/kameleon.json"), k);
    }
}
