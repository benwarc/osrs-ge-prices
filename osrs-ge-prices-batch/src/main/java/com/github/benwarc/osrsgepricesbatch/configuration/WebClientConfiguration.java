package com.github.benwarc.osrsgepricesbatch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient gePricesWebClient(@Value("${largest-response-size-kb}") int largestResponseSizeKb,
                                       @Value("${spring.application.name}") String applicationName) {

        var uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        return WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .codecs(codecs -> codecs
                        .defaultCodecs()
                        .maxInMemorySize(largestResponseSizeKb * 1000)
                )
                .defaultHeaders(httpHeaders -> httpHeaders
                        .set(HttpHeaders.USER_AGENT, applicationName)
                )
                .build();
    }
}
