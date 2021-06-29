package dev.kameleon.component;

import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@ApplicationScoped
public class ComponentService {

    @Inject
    Vertx vertx;

    public JsonArray components(String type, String version) throws Exception {
        String filename = "components-" + type + "-" + version + ".json";
        System.out.println(filename);
        try (InputStream inputStream = getClass().getResourceAsStream("/" + filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String components = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return new JsonArray(components);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonArray();
    }
}