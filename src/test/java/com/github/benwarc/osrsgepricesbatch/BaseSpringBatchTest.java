package com.github.benwarc.osrsgepricesbatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@SpringBatchTest
public class BaseSpringBatchTest {

    private static final String FIVE_MINUTE_PRICES_URL = "/5m";
    private static final String ITEM_MAPPING_URL = "/mapping";

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    private static MockWebServer mockWebServer;

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() throws IOException {
        mongoDBContainer.start();

        if (mockWebServer == null) {
            mockWebServer = new MockWebServer();
            mockWebServer.setDispatcher(getPricesClientDispatcher());
            mockWebServer.start();
        }

        System.setProperty("ge-prices.five-minute-prices-url", FIVE_MINUTE_PRICES_URL);
        System.setProperty("ge-prices.item-mapping-url", ITEM_MAPPING_URL);
    }

    @AfterAll
    static void afterAll() throws IOException {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
            mockWebServer = null;
        }
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);

        // Strip the scheme and trailing slash
        var mockWebServerUrl = mockWebServer.url("").toString();
        registry.add("ge-prices.base-url", () -> mockWebServerUrl.substring("http://".length(), mockWebServerUrl.length() - 1));
    }

    private static Dispatcher getPricesClientDispatcher() {
        return new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) {
                try {
                    return switch (request.getPath()) {
                        case FIVE_MINUTE_PRICES_URL -> new MockResponse()
                                .setResponseCode(200)
                                .setHeader("Content-Type", "application/json")
                                .setBody(fileToString("src/test/resources/mock-response/five-minute-prices.json"));
                        case ITEM_MAPPING_URL -> new MockResponse()
                                .setResponseCode(200)
                                .setHeader("Content-Type", "application/json")
                                .setBody(fileToString("src/test/resources/mock-response/item-mapping.json"));
                        default -> new MockResponse()
                                .setResponseCode(404);
                    };
                } catch (IOException e) {
                    return new MockResponse().setResponseCode(500);
                }
            }
        };
    }

    private static String fileToString(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }
}
