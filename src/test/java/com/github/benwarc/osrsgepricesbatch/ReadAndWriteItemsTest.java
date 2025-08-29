package com.github.benwarc.osrsgepricesbatch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.util.List;

class ReadAndWriteItemsTest extends BaseSpringBatchTest {

    @Autowired
    private Job readAndWriteItemsJob;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void beforeEach() {
        jobLauncherTestUtils.setJob(readAndWriteItemsJob);
    }

    @Test
    void readAndWriteItemsTest() throws Exception {
        List<ItemModel> expectedItems = objectMapper.readValue(new File("src/test/resources/expected/mongo-items.json"), new TypeReference<>() {});
        List<Integer> itemIds = expectedItems.stream().map(ItemModel::getItemId).toList();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        List<ItemModel> actualItems = mongoTemplate.find(Query.query(Criteria.where("itemId").in(itemIds)), ItemModel.class);
        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        Assertions.assertThat(actualItems).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedItems);
    }
}
