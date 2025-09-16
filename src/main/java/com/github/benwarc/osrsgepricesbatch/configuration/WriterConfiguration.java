package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import com.github.benwarc.osrsgepricesbeans.document.PriceDocument;
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
    public MongoItemWriter<ItemDocument> itemWriter() {
        return new MongoItemWriterBuilder<ItemDocument>()
                .template(mongoTemplate)
                .collection("items")
                .build();
    }

    @Bean
    public MongoItemWriter<PriceDocument> priceWriter() {
        return new MongoItemWriterBuilder<PriceDocument>()
                .template(mongoTemplate)
                .collection("prices")
                .build();
    }
}
