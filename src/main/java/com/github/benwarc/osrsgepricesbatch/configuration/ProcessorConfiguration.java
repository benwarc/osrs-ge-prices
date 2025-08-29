package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.mapper.ItemMapper;
import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfiguration {

    private final ItemMapper itemMapper;

    @Bean
    public ItemProcessor<Item, ItemModel> itemProcessor() {
        return itemMapper::dtoToModel;
    }
}
