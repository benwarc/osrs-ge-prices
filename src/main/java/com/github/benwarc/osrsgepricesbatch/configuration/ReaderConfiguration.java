package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReaderConfiguration {

    private final StringService stringService;

    @Bean
    public ItemReaderAdapter<String> reader() {
        var reader = new ItemReaderAdapter<String>();
        reader.setTargetObject(stringService);
        reader.setTargetMethod("getHelloWorld");
        return reader;
    }
}
