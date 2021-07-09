package org.apache.camel.kameleon.component;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.kameleon.model.CamelComponent;

public abstract class AbstractComponentService {

    protected CamelComponent getCamelComponent(String json, String type) {
        JsonObject metadata = new JsonObject(json);
        return new CamelComponent(
                getArtifactId(metadata, type),
                getTitle(metadata, type),
                getDescription(metadata, type),
                getSupportLevel(metadata, type),
                getLabels(metadata, type),
                getFirstVersion(metadata, type),
                getArtifactId(metadata, type),
                isDeprecated(metadata, type),
                nativeSupported(metadata, type)
                );
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

    protected Boolean nativeSupported(JsonObject metadata, String type) {
        try {
            return metadata.getJsonObject(type).getBoolean("nativeSupported");
        } catch (Exception e) {
            return false;
        }
    }
}
