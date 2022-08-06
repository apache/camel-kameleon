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

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/generator")
public class GeneratorResource {

    @Inject
    GeneratorService generatorService;

    @GET
    @Produces("application/zip")
    public Response get (@QueryParam("camelType") String camelType,
                @QueryParam("camelVersion") String camelVersion,
                @QueryParam("javaVersion") String javaVersion,
                @QueryParam("groupId") String groupId,
                @QueryParam("artifactId") String artifactId,
                @QueryParam("version") String version,
                @QueryParam("components") String components) throws Exception {
        String fileName = generatorService.generate(camelType,camelVersion, groupId, artifactId, version, javaVersion, components);
        File nf = new File(fileName);
        if (nf.exists()){
            return  Response.ok((Object) nf)
                    .type("application/zip")
                    .header("Content-Disposition", "attachment; filename=" + nf.getName())
                    .header("Filename", nf.getName()).build();
        }
        return Response.serverError().build();
    }
}


