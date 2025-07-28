package com.github.benwarc.osrsgepricesbatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbatch.client.GePricesClient;
import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.dto.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GePricesService {

    private final GePricesClient gePricesClient;
    private final ObjectMapper objectMapper;

    // Five minute prices
    private static final String DATA = "data";
    private static final String AVG_HIGH_PRICE = "avgHighPrice";
    private static final String HIGH_PRICE_VOLUME = "highPriceVolume";
    private static final String AVG_LOW_PRICE = "avgLowPrice";
    private static final String LOW_PRICE_VOLUME = "lowPriceVolume";
    private static final String TIMESTAMP = "timestamp";

    public List<Item> getItemMapping() {
        return gePricesClient.getItemMapping().orElse(Collections.emptyList());
    }

    public List<Price> getFiveMinutePrices() {
        String jsonClientResponse = gePricesClient.getFiveMinutePrices().orElse(null);

        JsonNode fiveMinutePrices;
        try {
            fiveMinutePrices = Objects.requireNonNull(objectMapper.readTree(jsonClientResponse));
        } catch (IllegalArgumentException | JsonProcessingException | NullPointerException e) {
            log.error("Exception thrown while deserializing five minute prices from JSON string", e);
            return Collections.emptyList();
        }
        JsonNode itemIdAndPriceMap = fiveMinutePrices.get(DATA);
        int timestamp = fiveMinutePrices.get(TIMESTAMP).asInt();

        var prices = new ArrayList<Price>();
        itemIdAndPriceMap.fields().forEachRemaining(mapEntry -> {
            var price = new Price(
                    Integer.parseInt(mapEntry.getKey()),
                    mapEntry.getValue().get(AVG_HIGH_PRICE).asInt(),
                    mapEntry.getValue().get(HIGH_PRICE_VOLUME).asInt(),
                    mapEntry.getValue().get(AVG_LOW_PRICE).asInt(),
                    mapEntry.getValue().get(LOW_PRICE_VOLUME).asInt(),
                    timestamp
            );
            prices.add(price);
        });
        return prices;
    }
}
