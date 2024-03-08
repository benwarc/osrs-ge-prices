package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.ItemDetails;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class WriterConfiguration {

    private final GePricesService gePricesService;

    @Bean
    public ItemWriterAdapter<Set<ItemDetails>> writer() {
        var writer = new ItemWriterAdapter<Set<ItemDetails>>();
        writer.setTargetObject(gePricesService);
        writer.setTargetMethod("writeItemDetails");
        return writer;
    }
}
