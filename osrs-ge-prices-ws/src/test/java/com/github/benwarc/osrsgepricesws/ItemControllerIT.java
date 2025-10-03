package com.github.benwarc.osrsgepricesws;

import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class ItemControllerIT extends BaseSpringBootTest {

    @Test
    void getItemByItemIdTest() {
        var itemId = 11507;
        var expectedBody = Stream.of(readFileAsType("src/test/resources/expected/item-dtos.json", ItemDto[].class))
                .filter(item -> Integer.valueOf(item.id()).equals(itemId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        RestAssured
                .given()
                    .port(localServerPort)
                    .pathParam("item-id", itemId)
                .when()
                    .get("/items/item-id/{item-id}")
                .then()
                    .statusCode(200)
                    .body(Matchers.is(writeValueAsString(expectedBody)));
    }

    @Test
    void getItemsByNameTest() {
        var expectedItemIds = List.of(11507, 7331);
        var expectedBody = Stream.of(readFileAsType("src/test/resources/expected/item-dtos.json", ItemDto[].class))
                .filter(item -> expectedItemIds.contains(item.id()))
                .toList();

        RestAssured
                .given()
                    .port(localServerPort)
                    .pathParam("name", "fire")
                .when()
                    .get("/items/name/{name}")
                .then()
                    .statusCode(200)
                    .body(Matchers.is(writeValueAsString(expectedBody)));
    }
}
