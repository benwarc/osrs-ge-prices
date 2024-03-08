package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.ItemDetails;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ReaderConfiguration {

    private final GePricesService gePricesService;

    @Bean
    public ItemReaderAdapter<Set<ItemDetails>> reader() {
        var reader = new ItemReaderAdapter<Set<ItemDetails>>();
        reader.setTargetObject(gePricesService);
        reader.setTargetMethod("readItemDetails");
        return reader;
    }
}
