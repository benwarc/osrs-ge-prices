package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
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
    public MongoItemWriter<Price> priceWriter() {
        return new MongoItemWriterBuilder<Price>()
                .template(mongoTemplate)
                .build();
    }
}
