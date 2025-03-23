package com.github.benwarc.osrsgepricesbatch;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.batch.core.Job;
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

    private static final MockWebServer mockWebServer = new MockWebServer();
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    protected Job job;
    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeAll
    static void beforeAll() throws IOException {
        mongoDBContainer.start();

        mockWebServer.setDispatcher(getPricesClientDispatcher());
        mockWebServer.start();

        System.setProperty("ge-prices.five-minute-prices-url", FIVE_MINUTE_PRICES_URL);
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void beforeEach() {
        jobLauncherTestUtils.setJob(job);
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
                    if (FIVE_MINUTE_PRICES_URL.equals(request.getPath())) {
                        return new MockResponse()
                                .setResponseCode(200)
                                .setBody(fileToString("src/test/resources/mock-response/five-minute-prices.json"));
                    } else {
                        return new MockResponse().setResponseCode(404);
                    }
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
