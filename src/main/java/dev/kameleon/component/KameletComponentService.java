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
