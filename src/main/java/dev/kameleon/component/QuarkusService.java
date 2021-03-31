package dev.kameleon.component;

import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuarkusService {

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

        List<String> components = componentList(version);
        List<Tuple2<String, String>> names = componentNames(version, "Component");

        components.forEach(name -> {
            Optional<Tuple2<String, String>> values = getQuarkusName(names, name);
            String component =  "camel-quarkus-" + name;
            if (!values.isEmpty()) {
                result.add(new JsonObject().put("component", component).put("name", values.get().getItem1()).put("type", values.get().getItem2()));
            }
        });

        return result;
    }

    private Optional<Tuple2<String, String>> getQuarkusName(List<Tuple2<String, String>> names, String component) {
        return names.stream()
                .filter(s -> s.getItem1().contains("xref:reference/extensions/" + component))
                .map(s -> {
                    Matcher m = p.matcher(s.getItem1());
                    return m.find() ? Tuple2.of(m.group(1), s.getItem2()) : null;
                })
                .findFirst();
    }

    private List<String> componentList(String version) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel-quarkus/"+version+"/extensions/pom.xml";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return getModules(resp.bodyAsString());
            } else {
                return new ArrayList<String>(0);
            }
        }).subscribe().asCompletionStage().get();
    }

    private List<String> getModules(String pom){
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new StringReader(pom));
            return model.getModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private List<Tuple2<String, String>> componentNames(String version, String componentName) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel-quarkus/" + version + "/docs/modules/ROOT/nav.adoc";
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