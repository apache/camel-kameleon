/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.kameleon.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.openapi.models.OasDocument;
import org.apache.camel.CamelContext;
import org.apache.camel.generator.openapi.RestDslGenerator;
import org.apache.camel.impl.lw.LightweightCamelContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileNotFoundException;
import java.util.Map;

@ApplicationScoped
public class RestDslGeneratorService {

    public String generate(String filename, String openapi) throws Exception {
        System.out.println(filename);
        System.out.println(openapi);
        final JsonNode node = filename.endsWith("json") ? readNodeFromJson(openapi) : readNodeFromYaml(openapi);
        OasDocument document = (OasDocument) Library.readDocument(node);
        try (CamelContext context = new LightweightCamelContext()) {
            return RestDslGenerator.toYaml(document).generate(context, true);
        }
    }

    private JsonNode readNodeFromJson(String openapi) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(openapi);
    }

    private JsonNode readNodeFromYaml(String openapi) throws FileNotFoundException {
        final ObjectMapper mapper = new ObjectMapper();
        Yaml loader = new Yaml(new SafeConstructor());
        Map map = loader.load(openapi);
        return mapper.convertValue(map, JsonNode.class);
    }
}
