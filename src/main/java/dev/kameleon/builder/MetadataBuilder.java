package dev.kameleon.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kameleon.data.CamelComponent;
import dev.kameleon.data.CamelType;
import dev.kameleon.data.CamelVersion;
import dev.kameleon.data.KameleonConfiguration;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MetadataBuilder {

    private static final Logger LOGGER = Logger.getLogger(MetadataBuilder.class.getName());

    private static final Map<String, List<CamelComponent>> componentCache = new HashMap<>(10);
    private static final String QUARKUS = "quarkus";
    private static final String CLASSIC = "classic";
    private static final String LEGACY = "legacy";

    private static Vertx vertx = Vertx.vertx();
    private static WebClient client = WebClient.create(vertx,
            new WebClientOptions().setDefaultPort(443).setSsl(true)
                    .setTrustAll(true));

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Generating components metadata");

        KameleonConfiguration kc = new KameleonConfiguration();

        readConfiguration().getTypes()
                .forEach(camelType -> camelType.getVersions()
                        .forEach(camelVersion -> createComponentFile(camelType, camelVersion))
                );
        System.exit(0);
    }

    private static void createComponentFile(CamelType camelType, CamelVersion camelVersion) {
        String filename = "components-" + camelType.getName() + "-" + camelVersion.getName() + ".json";
        LOGGER.info("Creating file: " + filename);
        String builder = getBuilderType(camelType, camelVersion);
        List<CamelComponent> list = getComponentList(builder, camelVersion.getName());
        JsonArray jsonArray = new JsonArray(list);
        Vertx.vertx().fileSystem().writeFileAndAwait("target/classes/" + filename, Buffer.buffer(jsonArray.toString()));
    }

    private static List<CamelComponent> getComponentList(String builderType, String version){
        String key = builderType+version;
        List<CamelComponent> result = null;
        if (!componentCache.containsKey(key)){
            switch (builderType){
                case QUARKUS:
                    result = QuarkusBuilder.getComponents(client, version);
                    break;
                case CLASSIC:
                    result = ClassicBuilder.getComponents(client, version);
                    break;
                case LEGACY:
                    result = LegacyBuilder.getComponents(client, version);
                    break;
            }
            componentCache.put(key, result);
        }
        return componentCache.get(key);
    }

    private static String getBuilderType(CamelType camelType, CamelVersion camelVersion){
        String builderType = null;
        if (QUARKUS.equals(camelType.getName())) {
            builderType = QUARKUS;
        } else if (camelVersion.getName().startsWith("2")) {
            builderType = LEGACY;
        } else {
            builderType = CLASSIC;
        }
        return builderType;
    }

    private static KameleonConfiguration readConfiguration() {
        KameleonConfiguration kc = null;
        try (InputStream inputStream = MetadataBuilder.class.getResourceAsStream("/kameleon.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String configuration = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper objectMapper = new ObjectMapper();
            kc = objectMapper.readValue(configuration, KameleonConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kc;
    }
}
