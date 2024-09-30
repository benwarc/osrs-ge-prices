package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.List;

@Configuration
public class StepConfiguration {

    @Bean
    public Step step(JobRepository jobRepository,
                     DataSourceTransactionManager transactionManager,
                     ItemReader<List<Item>> reader,
                     ItemProcessor<String, String> processor,
                     ItemWriter<List<Item>> writer) {

        return new StepBuilder("step", jobRepository)
                .<List<Item>, List<Item>> chunk(1, transactionManager)
                .reader(reader)
//                .processor(processor)
                .writer(writer)
                .build();
    }
}
