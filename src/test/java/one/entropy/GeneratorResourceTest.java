package one.entropy;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GeneratorResourceTest {

    @Test
    public void generateEndpoint() {
        given()
          .when().get("/generator/main/3.8.0/org.acme/demo/camel-timer,camel-log")
          .then()
             .statusCode(200);
    }

}