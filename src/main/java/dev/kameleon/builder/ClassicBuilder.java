package dev.kameleon.builder;

import dev.kameleon.data.CamelComponent;
import io.smallrye.mutiny.tuples.Tuple3;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.client.WebClient;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClassicBuilder extends AbstractBuilder {

    private static final Logger LOGGER = Logger.getLogger(ClassicBuilder.class.getName());

    public static List<CamelComponent> getComponents(WebClient client, String version){
        LOGGER.info("--- Classic components: " + version);
        List<CamelComponent> camelComponents = new ArrayList<>(200);
        try {
            List<Tuple3<String, String, String>> compTypes = List.of(
                    Tuple3.of("components", "component", "Component"),
                    Tuple3.of("dataformats", "dataformat", "Dataformat"),
                    Tuple3.of("languages", "language", "Language"),
                    Tuple3.of("others", "other", "Other")
            );

            for (int i = 0; i < compTypes.size(); i++) {
                Tuple3<String, String, String> compType = compTypes.get(i);
                List<String> components = componentList(client, compType.getItem1(), version);
                for (String name : components) {
                    JsonObject metadata = componentMetadata(client, version, compType.getItem1(), name);
                    if (!isDeprecated(metadata, compType.getItem2())) {
                        camelComponents.add(new CamelComponent(
                                "camel-" + name,
                                getTitle(metadata, compType.getItem2()),
                                compType.getItem3(),
                                getDescription(metadata, compType.getItem2()),
                                getSupportLevel(metadata, compType.getItem2()),
                                getLabels(metadata, compType.getItem2())));
                    }
                }
            }
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "", e);
        }
        return camelComponents;
    }

    private static List<String> componentList(WebClient client, String compType, String version) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel/camel-"+version+"/catalog/camel-catalog/src/generated/resources/org/apache/camel/catalog/"+compType+".properties";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return Arrays.stream(resp.bodyAsString().split("\\n")).collect(Collectors.toList());
            } else {
                return new ArrayList<String>(0);
            }
        }).subscribe().asCompletionStage().get();
    }

    protected static JsonObject componentMetadata(WebClient client, String version, String compType, String componentName) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel/camel-"+version+"/catalog/camel-catalog/src/generated/resources/org/apache/camel/catalog/"+compType+"/"+componentName+".json";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return resp.bodyAsJsonObject();
            } else {
                return new JsonObject();
            }
        }).subscribe().asCompletionStage().get();
    }
}