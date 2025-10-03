package com.github.benwarc.osrsgepricesbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {

    @Bean
    public Job readAndWriteItemsJob(JobRepository jobRepository, Step readAndWriteItemsStep) {
        return new JobBuilder("readAndWriteItemsJob", jobRepository)
                .start(readAndWriteItemsStep)
                .build();
    }

    @Bean
    public Job readAndWritePricesJob(JobRepository jobRepository, Step readAndWritePricesStep) {
        return new JobBuilder("readAndWritePricesJob", jobRepository)
                .start(readAndWritePricesStep)
                .build();
    }
}
