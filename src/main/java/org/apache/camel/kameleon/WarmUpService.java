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
package org.apache.camel.kameleon;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.eventbus.EventBus;

import org.apache.camel.kameleon.component.ComponentResource;
import org.apache.camel.kameleon.config.ConfigurationResource;
import org.apache.camel.kameleon.generator.ProjectGeneratorService;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WarmUpService {

    private static final Logger LOGGER = Logger.getLogger(WarmUpService.class.getName());

    @Inject
    EventBus eventBus;

    @Inject
    ConfigurationResource configurationResource;

    @Inject
    ComponentResource componentResource;

    @Inject
    ProjectGeneratorService projectGeneratorService;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Data warmup start...");
        if (!ProfileManager.getLaunchMode().isDevOrTest()) {
            configurationResource.getKc().getTypes()
                    .forEach(camelType -> camelType.getVersions()
                            .forEach(camelVersion -> camelVersion.getJavaVersions()
                                    .forEach(javaVersion -> eventBus.publish("warmup",
                                            new WarmupRequest(camelType.getName(), camelVersion.getName(), javaVersion))
                                    )
                            )
                    );
        }
    }

    @ConsumeEvent(value = "warmup", blocking = true)
    void warmup(WarmupRequest request) throws Exception {
        String type = request.getType();
        String version = request.getVersion();
        String javaVersion = request.javaVersion;
        LOGGER.info("Data warmup for " + type + " " + version);
        try {
            JsonArray componentArray = componentResource.components(type, version);
            List<String> componentList = componentArray.stream().map(o -> o.toString()).collect(Collectors.toList());
            String components = componentList.stream().limit(5).collect(Collectors.joining(","));
            projectGeneratorService.generate(type, version, "org.apache.camel.kameleon", "demo", "0.0.1", javaVersion, components);
            LOGGER.info("Data warmup done for " + type + " " + version);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public class WarmupRequest {
        public String type;
        public String version;
        public String javaVersion;

        public WarmupRequest() {
        }

        public WarmupRequest(String type, String version, String javaVersion) {
            this.type = type;
            this.version = version;
            this.javaVersion = javaVersion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getJavaVersion() {
            return javaVersion;
        }

        public void setJavaVersion(String javaVersion) {
            this.javaVersion = javaVersion;
        }
    }
}
