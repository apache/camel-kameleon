package dev.kameleon.builder;

import dev.kameleon.config.CamelComponent;
import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class QuarkusBuilder extends AbstractBuilder {

    private static final Logger LOGGER = Logger.getLogger(QuarkusBuilder.class.getName());

    public static List<CamelComponent> getComponents(WebClient client, String version) {
        LOGGER.fine("--- Quarkus components:");
        List<CamelComponent> camelComponents = new ArrayList<>(200);
        try {
            JsonArray result = new JsonArray();

            List<String> components = quarkusComponentList(client, version);
            String classicVersion = camelClassicVersion(client, version);

            for (String name : components) {
                String component = "camel-quarkus-" + name;
                JsonObject metadata = ClassicBuilder.componentMetadata(client, classicVersion, "components", name);
                camelComponents.add(new CamelComponent(
                        component,
                        getTitle(metadata, "component"),
                        "Component",
                        getDescription(metadata, "component"),
                        getLabels(metadata, "component")));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        return camelComponents;
    }

    private static List<String> quarkusComponentList(WebClient client, String version) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel-quarkus/" + version + "/extensions/pom.xml";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return getModules(resp.bodyAsString());
            } else {
                return new ArrayList<String>(0);
            }
        }).subscribe().asCompletionStage().get();
    }

    private static List<String> getModules(String pom) {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new StringReader(pom));
            return model.getModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private static String getParent(String pom) {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new StringReader(pom));
            return model.getParent().getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String camelClassicVersion(WebClient client, String version) throws ExecutionException, InterruptedException {
        String url = "https://raw.githubusercontent.com/apache/camel-quarkus/" + version + "/pom.xml";
        return client.getAbs(url).send().map(resp -> {
            if (resp.statusCode() == 200) {
                return getParent(resp.bodyAsString());
            } else {
                return null;
            }
        }).subscribe().asCompletionStage().get();
    }
}