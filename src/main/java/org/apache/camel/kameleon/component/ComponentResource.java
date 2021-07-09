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


import io.vertx.core.json.JsonArray;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/component")
public class ComponentResource {

    @Inject
    QuarkusComponentService quarkusComponentService;

    @Inject
    ClassicComponentService classicComponentService;

    @Inject
    KameletComponentService kameletComponentService;

    @GET
    @Path("/{type}/{version}")
    @Produces(MediaType.APPLICATION_JSON)
//    @CacheResult(cacheName = "components")
    public JsonArray components(@PathParam("type") String type, @PathParam("version") String version) throws Exception {
        switch (type) {
            case "quarkus":
                return quarkusComponentService.components();
            case "kamelet":
                return kameletComponentService.components();
            default:
                return classicComponentService.components(version);
        }
    }

}
