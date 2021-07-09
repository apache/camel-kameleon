package dev.kameleon.test;

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


import dev.kameleon.config.ConfigurationResource;
import dev.kameleon.model.KameleonConfiguration;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Assertions;

@QuarkusTest
public class ComponentResourceTest {

    @Inject
    ConfigurationResource configurationResource;

    @Test
    public void testComponents() {
        KameleonConfiguration kc = configurationResource.getKc();
        kc.getTypes().forEach(camelType ->
                camelType.getVersions().forEach(
                        camelVersion -> test(camelType.getName(), camelVersion.getName())
                )
        );
    }

    private void test(String type, String version){
        Response resp = given()
                .pathParam("type", type)
                .pathParam("version", version)
                .when().get("/component/{type}/{version}")
                .then().extract().response();

        List<HashMap<String, String>> list = resp.getBody().jsonPath().getList("");
        Assertions.assertTrue(list.size() > 100);
        Assertions.assertTrue(list.stream().filter(c -> c.get("name").contains("amqp")).count() > 0);
        Assertions.assertTrue(list.stream().filter(c -> c.get("name").contains("kafka")).count() > 0);
    }

}
