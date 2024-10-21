package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReaderConfiguration {

    private final GePricesService gePricesService;

    @Bean
    public ListItemReader<Price> priceReader() {
        return new ListItemReader<>(gePricesService.getFiveMinutePrices());
    }
}
