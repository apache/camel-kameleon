package dev.kameleon.component;

import dev.kameleon.data.CamelComponent;
import io.vertx.core.json.JsonArray;
import org.apache.camel.catalog.CamelCatalog;
import org.apache.camel.catalog.DefaultCamelCatalog;
import org.apache.camel.catalog.DefaultRuntimeProvider;
import org.apache.camel.catalog.quarkus.QuarkusRuntimeProvider;
import org.apache.maven.artifact.versioning.ComparableVersion;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClassicComponentService extends AbstractComponentService {

    public JsonArray components(String version) throws Exception {
        CamelCatalog catalog = new DefaultCamelCatalog();
        catalog.setRuntimeProvider(new DefaultRuntimeProvider());

        ComparableVersion camelVersion = new ComparableVersion(version);

        List<CamelComponent> list = new ArrayList<>();

        catalog.findComponentNames().forEach(name -> {
            String json = catalog.componentJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "component");
            if (!component.getDeprecated() && new ComparableVersion(component.getFirstVersion()).compareTo(camelVersion) != 1) {
                list.add(component);
            }
        });

        catalog.findDataFormatNames().forEach(name -> {
            String json = catalog.dataFormatJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "dataformat");
            if (!component.getDeprecated() && new ComparableVersion(component.getFirstVersion()).compareTo(camelVersion) != 1) {
                list.add(component);
            }
        });

        catalog.findLanguageNames().forEach(name -> {
            String json = catalog.languageJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "language");
            if (!component.getDeprecated() && new ComparableVersion(component.getFirstVersion()).compareTo(camelVersion) != 1) {
                list.add(component);
            }
        });

        catalog.findOtherNames().forEach(name -> {
            String json = catalog.otherJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "other");
            if (!component.getDeprecated() && new ComparableVersion(component.getFirstVersion()).compareTo(camelVersion) != 1) {
                list.add(component);
            }
        });

        return new JsonArray(list);
    }
}