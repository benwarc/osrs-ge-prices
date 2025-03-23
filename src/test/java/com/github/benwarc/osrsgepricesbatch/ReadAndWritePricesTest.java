package com.github.benwarc.osrsgepricesbatch;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

class ReadAndWritePricesTest extends BaseSpringBatchTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void readAndWritePricesTest() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        Assertions.assertEquals(194, mongoTemplate.findOne(Query.query(Criteria.where("itemId").is(2)), Price.class).avgHighPrice());
        Assertions.assertEquals(950, mongoTemplate.findOne(Query.query(Criteria.where("itemId").is(30)), Price.class).avgHighPrice());
    }
}
