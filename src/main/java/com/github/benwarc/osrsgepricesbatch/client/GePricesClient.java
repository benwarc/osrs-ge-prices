package com.github.benwarc.osrsgepricesbatch.client;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.properties.GePricesProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GePricesClient {

    private final WebClient gePricesWebClient;
    private final GePricesProperties gePricesProperties;

    private static final String HTTPS = "https";

    public Optional<List<Item>> getItemMapping() {
        try {
            return Optional.ofNullable(gePricesWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme(HTTPS)
                            .host(gePricesProperties.baseUrl())
                            .path(gePricesProperties.itemMappingUrl())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Item>>() {
                    })
                    .block());
        } catch (Exception e) {
            log.error("{} thrown while getting item mappings", e.getCause().toString());
            return Optional.empty();
        }
    }

    public Optional<String> getFiveMinutePrices() {
        try {
            return Optional.ofNullable(gePricesWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme(HTTPS)
                            .host(gePricesProperties.baseUrl())
                            .path(gePricesProperties.fiveMinutePricesUrl())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block());
        } catch (Exception e) {
            log.error("{} thrown while getting five minute prices", e.getCause().toString());
            return Optional.empty();
        }
    }
}
