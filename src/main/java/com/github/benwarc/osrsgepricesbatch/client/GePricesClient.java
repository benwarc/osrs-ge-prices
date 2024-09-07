package com.github.benwarc.osrsgepricesbatch.client;

import com.github.benwarc.osrsgepricesbatch.dto.ItemDetails;
import com.github.benwarc.osrsgepricesbatch.properties.GePricesProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class GePricesClient {

    private final WebClient gePricesWebClient;
    private final GePricesProperties gePricesProperties;

    private static final String HTTPS = "https";

    public Set<ItemDetails> getItemDetails() {
        try {
            return gePricesWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme(HTTPS)
                            .host(gePricesProperties.baseUrl())
                            .path(gePricesProperties.itemDetailsUrl())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<ItemDetails>>() {
                    })
                    .block();
        } catch (Exception e) {
            log.error("{} thrown while attempting to get item details", e.getCause().toString());
            return Collections.emptySet();
        }
    }

    public String getFiveMinutePrices() {
        try {
            return gePricesWebClient
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
                    .block();
        } catch (Exception e) {
            log.error("{} thrown while attempting to get five minute prices", e.getCause().toString());
            return "";
        }
    }
}
