package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.service.GePricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WriterConfiguration {

    private final GePricesService gePricesService;

    @Bean
    public ItemWriterAdapter<List<Item>> writer() {
        var writer = new ItemWriterAdapter<List<Item>>();
        writer.setTargetObject(gePricesService);
        writer.setTargetMethod("writeItemDetails");
        return writer;
    }
}
