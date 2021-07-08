package dev.kameleon.component;

import dev.kameleon.model.KameletComponent;
import io.vertx.core.json.JsonArray;
import org.apache.camel.kamelets.catalog.KameletsCatalog;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class KameletComponentService {

    public JsonArray components() throws Exception {
        KameletsCatalog catalog = new KameletsCatalog();
        List<KameletComponent> list = catalog.getKamelets().entrySet().stream()
                .map(e -> new KameletComponent(
                        e.getValue().getMetadata().getName(),
                        e.getValue().getSpec().getDefinition().getTitle(),
                        e.getValue().getSpec().getDefinition().getDescription().split("\\r?\\n")[0],
                        e.getValue().getMetadata().getAnnotations().get("camel.apache.org/kamelet.support.level"),
                        List.of(e.getValue().getMetadata().getLabels().get("camel.apache.org/kamelet.type")),
                        e.getValue().getMetadata().getAnnotations().get("camel.apache.org/kamelet.group"),
                        e.getValue().getMetadata().getAnnotations().get("camel.apache.org/kamelet.icon")
                )).collect(Collectors.toList());
        return new JsonArray(list);
    }
}