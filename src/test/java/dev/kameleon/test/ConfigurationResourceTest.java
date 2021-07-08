package dev.kameleon.test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ConfigurationResourceTest {

    @ConfigProperty(name = "application.version")
    String version;

    @Test
    public void testVersion() {
        given()
                .when().get("/configuration/version")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is(version));
    }

    @Test
    public void testConfiguration() {

        given()
                .when().get("/configuration")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("types.size()", is(5))
                .body("types[0].name", equalTo("standalone"))
                .body("types[1].name", equalTo("spring"))
                .body("types[2].name", equalTo("cdi"))
                .body("types[3].name", equalTo("quarkus"));

    }
}