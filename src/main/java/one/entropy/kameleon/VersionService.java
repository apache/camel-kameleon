package one.entropy.kameleon;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ApplicationScoped
public class VersionService {

    private final static JsonArray QUARKUS_DEFAULT = new JsonArray().add("1.7.0");
    private final static JsonArray CLASSIC_DEFAULT = new JsonArray().add("3.9.0");
    private final static String QUARKUS_VERSION = "1.12.2.Final";


    @Inject
    Vertx vertx;

    private WebClient client;

    @PostConstruct
    void initialize() {
        this.client = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("search.maven.org").setDefaultPort(443).setSsl(true)
                        .setTrustAll(true));
    }

    public JsonArray getVersions(String type) throws Exception {
        List<JsonArray> pages = Arrays.asList(getPage(type, 0), getPage(type, 1), getPage(type, 2)); // TODO: Fix
        JsonArray versions = pages.stream().reduce((j1, j2) -> j2.addAll(j1)).get();
        List result = versions.stream().sorted((o1, o2) -> o2.toString().compareTo(o1.toString())).collect(Collectors.toList());
        if (versions.isEmpty()) {
            return "quarkus".equals(type) ? QUARKUS_DEFAULT : CLASSIC_DEFAULT;
        } else {
            return new JsonArray(result);
        }
    }

    public String getQuarkusVersion(String camelQuarkusVersion) throws Exception {
        String url = "https://raw.githubusercontent.com/apache/camel-quarkus/"+camelQuarkusVersion+"/pom.xml";
        String pom = client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return resp.bodyAsString();
            } else {
                return QUARKUS_VERSION;
            }
        }).subscribe().asCompletionStage().get();
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new StringReader(pom));
        return model.getProperties().getProperty("quarkus.version");
    }

    private JsonArray getPage(String type, int page) throws ExecutionException, InterruptedException {
        String url = "quarkus".equals(type)
                ? "/solrsearch/select?q=g:org.apache.camel.quarkus%20AND%20a:camel-quarkus&core=gav&start=" + page * 20 + "&rows=20"
                : "/solrsearch/select?q=g:org.apache.camel.archetypes%20AND%20a:camel-archetype-" + type + "&core=gav&start=" + page * 20 + "&rows=20";
        return client.get(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                JsonArray versions = new JsonArray();
                JsonArray docs = resp.bodyAsJsonObject().getJsonObject("response").getJsonArray("docs");
                docs.stream().forEach(o -> {
                    String version = ((JsonObject) o).getString("v");
                    if (!version.contains("-")) versions.add(version);
                });
                return versions;
            } else {
                return new JsonArray();
            }
        }).subscribe().asCompletionStage().get();
    }


}