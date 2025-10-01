package com.github.benwarc.osrsgepricesws;

import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

public class PriceControllerIT extends BaseSpringBootTest {

    @Test
    void getLatestFiveMinuteAveragePriceByItemIdTest() {
        var expectedBody = Stream.of(readFileAsType("src/test/resources/expected/price-dtos.json", PriceDto[].class))
                .max(Comparator.comparing(PriceDto::timestamp))
                .orElseThrow(RuntimeException::new);

        RestAssured
                .given()
                    .port(localServerPort)
                    .pathParam("item-id", 7331)
                .when()
                    .get("/prices/item-id/{item-id}")
                .then()
                    .statusCode(200)
                    .body(Matchers.is(writeValueAsString(expectedBody)));
    }
}
