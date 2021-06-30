package dev.kameleon.component;

import dev.kameleon.data.CamelComponent;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractComponentService {

    protected CamelComponent getCamelComponent(String json, String type) {
        JsonObject metadata = new JsonObject(json);
        return new CamelComponent(
                getArtifactId(metadata, type),
                getTitle(metadata, type),
                getDescription(metadata, type),
                getSupportLevel(metadata, type),
                getFirstVersion(metadata, type),
                getArtifactId(metadata,type),
                isDeprecated(metadata,type),
                getLabels(metadata, type));
    }

    protected String getArtifactId(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getString("artifactId");
        } catch (Exception e) {
            return "";
        }
    }

    protected String getFirstVersion(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getString("firstVersion");
        } catch (Exception e) {
            return "";
        }
    }

    protected String getDescription(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getString("description");
        } catch (Exception e) {
            return "";
        }
    }

    protected String getSupportLevel(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getString("supportLevel");
        } catch (Exception e) {
            return "Stable";
        }
    }

    protected String getTitle(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getString("title");
        } catch (Exception e) {
            return "";
        }
    }

    protected List<String> getLabels(JsonObject metadata, String type) {
        try {
            return Arrays.asList(metadata.getJsonObject(type).getString("label").split(","));
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }

    protected Boolean isDeprecated(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getBoolean("deprecated");
        } catch (Exception e) {
            return false;
        }
    }
}