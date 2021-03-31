package dev.kameleon.component;

import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;
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
public class ClassicService {

    private static final Pattern p = Pattern.compile("\\[([^\\]]+)\\]");

    @Inject
    Vertx vertx;

    private WebClient client;

    @PostConstruct
    void initialize() {
        this.client = WebClient.create(vertx,
                new WebClientOptions().setDefaultPort(443).setSsl(true)
                        .setTrustAll(true));
    }

    public JsonArray components(String version) throws Exception {
        JsonArray result = new JsonArray();

        List<Tuple3<String, String, String>> compTypes = List.of(
           Tuple3.of("components", "ROOT", "Component"),
           Tuple3.of("dataformats", "dataformats", "Dataformat"),
           Tuple3.of("languages", "languages", "Language"),
           Tuple3.of("others", "others", "Other")
        );

        List<String> components = new ArrayList<>();
        List<Tuple2<String, String>> names = new ArrayList<>();

        for (int i = 0; i < compTypes.size(); i++) {
            Tuple3<String, String, String> compType = compTypes.get(i);
            components.addAll(componentList(compType.getItem1(), version));
            names.addAll(componentNames(version, compType.getItem2(), compType.getItem3()));
        }

        components.forEach(name -> {
            Optional<Tuple2<String, String>> values = getClassicName(names, name);
            if (!values.isEmpty()) {
                result.add(new JsonObject().put("component", name).put("name", values.get().getItem1()).put("type", values.get().getItem2()));
            }
        });

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

    private List<String> componentList(String compType, String version) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel/camel-"+version+"/catalog/camel-catalog/src/generated/resources/org/apache/camel/catalog/"+compType+".properties";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return Arrays.stream(resp.bodyAsString().split("\\n")).collect(Collectors.toList());
            } else {
                return new ArrayList<String>(0);
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