package dev.kameleon.builder;

import dev.kameleon.config.CamelComponent;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.ext.web.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LegacyBuilder extends AbstractBuilder {

    private static final Logger LOGGER = Logger.getLogger(LegacyBuilder.class.getName());
    private static final Pattern p = Pattern.compile("\\[([^\\]]+)\\]");

    public static List<CamelComponent> getComponents(WebClient client, String version) {
        LOGGER.info("--- Legacy components: " + version);
        List<CamelComponent> camelComponents = new ArrayList<>(200);
        try {
            JsonArray components = componentList(client, version);
            List<Tuple2<String, String>> names = new ArrayList<>();
            names.addAll(componentNames(client, version, "ROOT", "Component"));
            names.addAll(componentNames(client, version, "dataformats", "Dataformat"));
            names.addAll(componentNames(client, version, "languages", "Language"));
            names.addAll(componentNames(client, version, "others", "Other"));

            for (int i = 0; i < components.size(); i++) {
                String component = components.getJsonObject(i).getString("name");
                Optional<Tuple2<String, String>> values = getClassicName(names, component);
                if (!values.isEmpty()) {
                    camelComponents.add(new CamelComponent(
                            component,
                            values.get().getItem1(),
                            values.get().getItem2(),
                            values.get().getItem1(),
                            new ArrayList<>(0)));
                }
            }
        } catch (
                Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        return camelComponents;
    }

    private static Optional<Tuple2<String, String>> getClassicName(List<Tuple2<String, String>> names, String component) {
        return names.stream()
                .filter(s -> s.getItem1().contains("xref:" + component.replace("camel-", "")))
                .map(s -> {
                    Matcher m = p.matcher(s.getItem1());
                    return m.find() ? Tuple2.of(m.group(1), s.getItem2()) : null;
                })
                .findFirst();
    }

    private static JsonArray componentList(WebClient client, String version) throws ExecutionException, InterruptedException {
        String url = "https://api.github.com/repos/apache/camel/contents/components?ref=camel-" + version;
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return resp.bodyAsJsonArray();
            } else {
                return new JsonArray();
            }
        }).subscribe().asCompletionStage().get();
    }

    private static List<Tuple2<String, String>> componentNames(WebClient client, String version, String folder, String componentName) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel/camel-" + version + "/docs/components/modules/" + folder + "/nav.adoc";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return Arrays.asList(resp.bodyAsString().split("\\n"));
            } else {
                return new ArrayList<String>(0);
            }
        }).subscribe().asCompletionStage().get()
                .stream().map(s -> Tuple2.of(s, componentName))
                .collect(Collectors.toList());
    }
}