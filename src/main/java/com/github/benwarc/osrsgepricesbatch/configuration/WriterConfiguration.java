package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WriterConfiguration {

    private final StringService stringService;

    @Bean
    public ItemWriterAdapter<String> writer() {
        var writer = new ItemWriterAdapter<String>();
        writer.setTargetObject(stringService);
        writer.setTargetMethod("logString");
        return writer;
    }
}
