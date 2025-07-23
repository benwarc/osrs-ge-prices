package com.github.benwarc.osrsgepricesbatch.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.dto.Price;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ReaderConfiguration {

    private final GePricesService gePricesService;
    private final ObjectMapper objectMapper;

    @Bean
    public JsonItemReader<Item> itemReader() {
        return new JsonItemReaderBuilder<Item>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Item.class))
                .resource(new ByteArrayResource(
                        this.serializeObjectListIntoByteArray(
                                gePricesService.getItemMapping())))
                .name("itemReader")
                .build();
    }

    @Bean
    public JsonItemReader<Price> priceReader() {
        return new JsonItemReaderBuilder<Price>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Price.class))
                .resource(new ByteArrayResource(
                        this.serializeObjectListIntoByteArray(
                                gePricesService.getFiveMinutePrices())))
                .name("priceReader")
                .build();
    }

    private byte[] serializeObjectListIntoByteArray(List<?> objects) {
        try {
            return objectMapper.writeValueAsBytes(objects);
        } catch (JsonProcessingException e) {
            log.error("Exception thrown while serializing object list into byte array", e);
            return new byte[0];
        }
    }
}
