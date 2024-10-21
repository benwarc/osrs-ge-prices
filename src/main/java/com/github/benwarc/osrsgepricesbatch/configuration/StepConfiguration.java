package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class StepConfiguration {

    @Bean
    public Step readAndWritePrices(JobRepository jobRepository,
                                   DataSourceTransactionManager transactionManager,
                                   ItemReader<Price> priceReader,
                                   ItemWriter<Price> priceWriter,
                                   ItemReadListener<Price> priceReadListener,
                                   ItemWriteListener<Price> priceWriteListener) {

        return new StepBuilder("readAndWritePrices", jobRepository)
                .<Price, Price> chunk(200, transactionManager)
                .reader(priceReader)
                .writer(priceWriter)
                .listener(priceReadListener)
                .listener(priceWriteListener)
                .build();
    }
}
