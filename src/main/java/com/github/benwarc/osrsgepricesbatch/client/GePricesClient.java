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

    public Set<ItemDetails> getItemDetails() {
        try {
            return gePricesWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
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
}
