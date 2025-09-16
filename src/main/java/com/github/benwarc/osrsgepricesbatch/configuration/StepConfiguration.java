package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import com.github.benwarc.osrsgepricesbeans.document.PriceDocument;
import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class StepConfiguration {

    @Bean
    public Step readAndWriteItemsStep(JobRepository jobRepository,
                                      DataSourceTransactionManager transactionManager,
                                      ItemReader<ItemDto> itemReader,
                                      ItemProcessor<ItemDto, ItemDocument> itemProcessor,
                                      ItemWriter<ItemDocument> itemWriter,
                                      ItemReadListener<ItemDto> osrsItemReadListener,
                                      ItemProcessListener<ItemDto, ItemDocument> osrsItemProcessListener,
                                      ItemWriteListener<ItemDocument> osrsItemWriteListener) {

        return new StepBuilder("readAndWriteItemsStep", jobRepository)
                .<ItemDto, ItemDocument> chunk(200, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(osrsItemReadListener)
                .listener(osrsItemProcessListener)
                .listener(osrsItemWriteListener)
                .build();
    }

    @Bean
    public Step readAndWritePricesStep(JobRepository jobRepository,
                                       DataSourceTransactionManager transactionManager,
                                       ItemReader<PriceDto> priceReader,
                                       ItemWriter<PriceDocument> priceWriter,
                                       ItemReadListener<PriceDto> priceReadListener,
                                       ItemWriteListener<PriceDocument> priceWriteListener) {

        return new StepBuilder("readAndWritePricesStep", jobRepository)
                .<PriceDto, PriceDocument> chunk(200, transactionManager)
                .reader(priceReader)
                .writer(priceWriter)
                .listener(priceReadListener)
                .listener(priceWriteListener)
                .build();
    }
}
