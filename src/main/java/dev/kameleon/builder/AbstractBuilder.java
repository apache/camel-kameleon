package dev.kameleon.builder;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractBuilder {

    static String getDescription(JsonObject metadata, String type){
        try {
            return metadata.getJsonObject(type).getString("description");
        } catch (Exception e){
            return "";
        }
    }

    static String getTitle(JsonObject metadata, String type){
        try {
            return metadata.getJsonObject(type).getString("title");
        } catch (Exception e){
            return "";
        }
    }

    static List<String> getLabels(JsonObject metadata, String type){
        try {
            return Arrays.asList(metadata.getJsonObject(type).getString("label").split(","));
        } catch (Exception e){
            return new ArrayList<>(0);
        }
    }
}