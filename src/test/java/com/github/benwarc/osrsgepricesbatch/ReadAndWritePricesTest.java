package com.github.benwarc.osrsgepricesbatch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benwarc.osrsgepricesbatch.model.PriceModel;
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

class ReadAndWritePricesTest extends BaseSpringBatchTest {

    @Autowired
    private Job readAndWritePricesJob;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void beforeEach() {
        jobLauncherTestUtils.setJob(readAndWritePricesJob);
    }

    @Test
    void readAndWritePricesTest() throws Exception {
        List<PriceModel> expectedPrices = objectMapper.readValue(new File("src/test/resources/expected/mongo-prices.json"), new TypeReference<>() {});
        List<Integer> itemIds = expectedPrices.stream().map(PriceModel::getItemId).toList();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        List<PriceModel> actualPrices = mongoTemplate.find(Query.query(Criteria.where("itemId").in(itemIds)), PriceModel.class);
        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        Assertions.assertThat(actualPrices).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedPrices);
    }
}
