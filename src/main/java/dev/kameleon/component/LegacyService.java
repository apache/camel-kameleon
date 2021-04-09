package dev.kameleon.component;

import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class LegacyService {

    private static final Pattern p = Pattern.compile("\\[([^\\]]+)\\]");

    @Inject
    Vertx vertx;

    private WebClient client;

    @PostConstruct
    void initialize() {
        this.client = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("api.github.com").setDefaultPort(443).setSsl(true)
                        .setTrustAll(true));
    }

    public JsonArray components(String version) throws Exception {

        JsonArray components = componentList(version);
        List<Tuple2<String, String>> names = new ArrayList<>();
        names.addAll(componentNames(version, "ROOT", "Component"));
        names.addAll(componentNames(version, "dataformats", "Dataformat"));
        names.addAll(componentNames(version, "languages", "Language"));
        names.addAll(componentNames(version, "others", "Other"));

        JsonArray result = new JsonArray();
        for (int i = 0; i < components.size(); i++) {
            String name = components.getJsonObject(i).getString("name");
            Optional<Tuple2<String, String>> values = getClassicName(names, name);
            if (!values.isEmpty()) {
                result.add(new JsonObject().put("component", name).put("name", values.get().getItem1()).put("type", values.get().getItem2()));
            }
        }
        return result;
    }

    private Optional<Tuple2<String, String>> getClassicName(List<Tuple2<String, String>> names, String component) {
        return names.stream()
                .filter(s -> s.getItem1().contains("xref:" + component.replace("camel-", "")))
                .map(s -> {
                    Matcher m = p.matcher(s.getItem1());
                    return m.find() ? Tuple2.of(m.group(1), s.getItem2()) : null;
                })
                .findFirst();
    }

    private JsonArray componentList(String version) throws ExecutionException, InterruptedException {
        String url = "/repos/apache/camel/contents/components?ref=camel-" + version;
        return client.get(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return resp.bodyAsJsonArray();
            } else {
                System.out.println(resp.statusCode());
                System.out.println(resp.bodyAsString());
                return new JsonArray();
            }
        }).subscribe().asCompletionStage().get();
    }

    private List<Tuple2<String, String>> componentNames(String version, String folder, String componentName) throws ExecutionException, InterruptedException {
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