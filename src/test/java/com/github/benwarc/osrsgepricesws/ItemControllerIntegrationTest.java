package com.github.benwarc.osrsgepricesws;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class ItemControllerIntegrationTest extends BaseSpringBootTest {

    @Test
    void getItemsByNameTest() {
        var expectedBody = readFileAsString("src/test/resources/expected/item-dtos-matching-fire.json");

        RestAssured
                .given()
                    .port(localServerPort)
                    .pathParam("name", "fire")
                .when()
                    .get("/items/{name}")
                .then()
                    .statusCode(200)
                    .body(Matchers.is(expectedBody));
    }
}
