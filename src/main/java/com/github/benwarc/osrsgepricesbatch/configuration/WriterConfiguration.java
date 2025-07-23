package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import com.github.benwarc.osrsgepricesbatch.model.PriceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class WriterConfiguration {

    private final MongoTemplate mongoTemplate;

    @Bean
    public MongoItemWriter<ItemModel> itemWriter() {
        return new MongoItemWriterBuilder<ItemModel>()
                .template(mongoTemplate)
                .collection("items")
                .build();
    }

    @Bean
    public MongoItemWriter<PriceModel> priceWriter() {
        return new MongoItemWriterBuilder<PriceModel>()
                .template(mongoTemplate)
                .collection("prices")
                .build();
    }
}
