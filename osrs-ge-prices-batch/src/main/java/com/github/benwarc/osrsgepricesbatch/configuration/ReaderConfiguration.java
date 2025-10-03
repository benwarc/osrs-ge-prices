package com.github.benwarc.osrsgepricesbatch.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
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
    public JsonItemReader<ItemDto> itemReader() {
        return new JsonItemReaderBuilder<ItemDto>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(ItemDto.class))
                .resource(new ByteArrayResource(
                        this.serializeObjectListIntoByteArray(
                                gePricesService.getItemMapping())))
                .name("itemReader")
                .build();
    }

    @Bean
    public JsonItemReader<PriceDto> priceReader() {
        return new JsonItemReaderBuilder<PriceDto>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(PriceDto.class))
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
