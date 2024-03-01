package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfiguration {

    private final StringService stringService;

    @Bean
    public ItemProcessorAdapter<String, String> processor() {
        var processor = new ItemProcessorAdapter<String, String>();
        processor.setTargetObject(stringService);
        processor.setTargetMethod("stringToUpperCase");
        return processor;
    }
}
