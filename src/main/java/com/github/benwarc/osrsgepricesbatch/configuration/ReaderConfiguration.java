package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;

@Configuration
@RequiredArgsConstructor
public class ReaderConfiguration {

    private final GePricesService gePricesService;

    @Bean
    public JsonItemReader<Price> priceReader() {
        return new JsonItemReaderBuilder<Price>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Price.class))
                .resource(new ByteArrayResource(gePricesService.getFiveMinutePricesAsByteArray()))
                .name("priceReader")
                .build();
    }
}
