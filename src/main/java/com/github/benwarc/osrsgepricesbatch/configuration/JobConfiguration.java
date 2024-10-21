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
    public Job job(JobRepository jobRepository, Step readAndWritePrices) {
        return new JobBuilder("job", jobRepository)
                .start(readAndWritePrices)
                .build();
    }
}
