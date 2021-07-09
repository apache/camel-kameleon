package dev.kameleon.component;

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


import dev.kameleon.model.CamelComponent;
import io.vertx.core.json.JsonArray;
import org.apache.camel.catalog.CamelCatalog;
import org.apache.camel.catalog.DefaultCamelCatalog;
import org.apache.camel.catalog.quarkus.QuarkusRuntimeProvider;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class QuarkusComponentService extends AbstractComponentService {

    public JsonArray components() throws Exception {
        CamelCatalog catalog = new DefaultCamelCatalog();
        catalog.setRuntimeProvider(new QuarkusRuntimeProvider());
        List<CamelComponent> list = new ArrayList<>();

        catalog.findComponentNames().forEach(name -> {
            String json = catalog.componentJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "component");
            if (!component.getDeprecated()) {
                list.add(component);
            }
        });

        catalog.findDataFormatNames().forEach(name -> {
            String json = catalog.dataFormatJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "dataformat");
            if (!component.getDeprecated()) {
                list.add(component);
            }
        });

        catalog.findLanguageNames().forEach(name -> {
            String json = catalog.languageJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "language");
            if (!component.getDeprecated()) {
                list.add(component);
            }
        });

        catalog.findOtherNames().forEach(name -> {
            String json = catalog.otherJSonSchema(name);
            CamelComponent component = getCamelComponent(json, "other");
            if (!component.getDeprecated()) {
                list.add(component);
            }
        });

        return new JsonArray(list);
    }
}
