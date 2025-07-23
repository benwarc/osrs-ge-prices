package com.github.benwarc.osrsgepricesbatch;

import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        Assertions.assertEquals("3rd age amulet", mongoTemplate.findOne(Query.query(Criteria.where("id").is(10344)), ItemModel.class).getName());
        Assertions.assertEquals("3rd age axe", mongoTemplate.findOne(Query.query(Criteria.where("id").is(20011)), ItemModel.class).getName());
    }
}
