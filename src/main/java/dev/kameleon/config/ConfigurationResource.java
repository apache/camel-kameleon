package dev.kameleon.config;

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


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kameleon.model.KameleonConfiguration;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.stream.Collectors;

@Path("/configuration")
public class ConfigurationResource {

    @ConfigProperty(name = "application.version")
    String version;

    private String configuration;
    private KameleonConfiguration kc;

    void onStart(@Observes StartupEvent ev) {
        readConfiguration();
    }

    @GET
    @Produces("application/json")
    public Response getConfiguration() throws Exception {
        return Response.ok(kc).build();
    }

    @GET
    @Path("/version")
    @Produces("text/plain")
    public Response getVersion() throws Exception {
        return Response.ok(version).build();
    }

    public KameleonConfiguration getKc() {
        readConfiguration();
        return kc;
    }

    private void readConfiguration() {
        if (kc == null) {
            try (InputStream inputStream = getClass().getResourceAsStream("/kameleon.json");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                configuration = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                kc = objectMapper.readValue(configuration, KameleonConfiguration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


