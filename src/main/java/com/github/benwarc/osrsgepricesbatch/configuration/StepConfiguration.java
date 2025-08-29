package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.dto.Price;
import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import com.github.benwarc.osrsgepricesbatch.model.PriceModel;
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
                                      ItemReader<Item> itemReader,
                                      ItemProcessor<Item, ItemModel> itemProcessor,
                                      ItemWriter<ItemModel> itemWriter,
                                      ItemReadListener<Item> osrsItemReadListener,
                                      ItemProcessListener<Item, ItemModel> osrsItemProcessListener,
                                      ItemWriteListener<ItemModel> osrsItemWriteListener) {

        return new StepBuilder("readAndWriteItemsStep", jobRepository)
                .<Item, ItemModel> chunk(200, transactionManager)
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
                                       ItemReader<Price> priceReader,
                                       ItemWriter<PriceModel> priceWriter,
                                       ItemReadListener<Price> priceReadListener,
                                       ItemWriteListener<PriceModel> priceWriteListener) {

        return new StepBuilder("readAndWritePricesStep", jobRepository)
                .<Price, PriceModel> chunk(200, transactionManager)
                .reader(priceReader)
                .writer(priceWriter)
                .listener(priceReadListener)
                .listener(priceWriteListener)
                .build();
    }
}
