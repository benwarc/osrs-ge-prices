package com.github.benwarc.osrsgepricesbatch.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ge-prices")
public record GePricesProperties(String scheme,
                                 String baseUrl,
                                 String itemMappingUrl,
                                 String fiveMinutePricesUrl) {
}
