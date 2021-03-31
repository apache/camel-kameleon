package dev.kameleon;

import dev.kameleon.component.ComponentResource;
import dev.kameleon.version.VersionService;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WarmUpService {

    private static final Logger LOGGER = Logger.getLogger(WarmUpService.class.getName());

    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Data warmup start...");
        vertx.eventBus().publish("warmup", "");
    }

    @Inject
    VersionService versionService;

    @Inject
    ComponentResource componentResource;

    @Inject
    GeneratorService generatorService;

    @ConsumeEvent(value = "warmup", blocking = true)
    void warmup(String message) throws Exception {
        Arrays.asList("main", "spring", "quarkus", "cdi").stream().forEach(type -> {
            try {
                LOGGER.info("Data warmup for " + type);
                Uni<List<String>> versions = versionService.getVersions(type);
                String version = versions.subscribe().asCompletionStage().get().get(0);
                JsonArray componentArray = componentResource.components(type, version);
                String components = componentArray.stream().map(o -> o.toString()).collect(Collectors.joining(","));
                generatorService.generate(type, version, "dev.kameleon", "demo", "0.0.1", components);
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
        LOGGER.info("Data warmup done.");
    }

}