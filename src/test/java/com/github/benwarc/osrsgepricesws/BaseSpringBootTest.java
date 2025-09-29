package com.github.benwarc.osrsgepricesws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
public class BaseSpringBootTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int localServerPort;

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        initMongoDBContainer();
    }

    @AfterEach
    void afterEach() {
        mongoTemplate.remove(new Query(), ItemDocument.class);
    }

    private void initMongoDBContainer() throws IOException {
        List<ItemDocument> items = List.of(
                objectMapper.readValue(
                        Files.readAllBytes(Paths.get("src/test/resources/mongo/item-documents.json")),
                        ItemDocument[].class));

        mongoTemplate.insertAll(items);
    }

    protected <T> T readFileAsType(String filePath, Class<T> type) {
        try {
            byte[] fileAsBytes = Files.readAllBytes(Paths.get(filePath));
            return objectMapper.readValue(fileAsBytes, type);
        } catch (IOException e) {
            log.error("Exception thrown while attempting to read file: {}", filePath, e);
            return null;
        }
    }

    protected String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Exception thrown while attempting to write value as string: {}", value, e);
            return null;
        }
    }
}
