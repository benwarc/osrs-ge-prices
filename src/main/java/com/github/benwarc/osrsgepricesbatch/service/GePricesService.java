package com.github.benwarc.osrsgepricesbatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbatch.client.GePricesClient;
import com.github.benwarc.osrsgepricesbatch.dto.ItemDetails;
import com.github.benwarc.osrsgepricesbatch.dto.ItemPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GePricesService {

    private final GePricesClient gePricesClient;
    private final ObjectMapper objectMapper;

    private boolean readItemDetails;

    // Five minute prices
    private static final String DATA = "data";
    private static final String AVG_HIGH_PRICE = "avgHighPrice";
    private static final String HIGH_PRICE_VOLUME = "highPriceVolume";
    private static final String AVG_LOW_PRICE = "avgLowPrice";
    private static final String LOW_PRICE_VOLUME = "lowPriceVolume";
    private static final String TIMESTAMP = "timestamp";

    public Set<ItemDetails> readItemDetails() {
        if (!readItemDetails) {
            readItemDetails = true;
            return gePricesClient.getItemDetails();
        } else {
            readItemDetails = false;
            // Adheres to the ItemReader.read contract such that null is returned when all data is exhausted
            return null;
        }
    }

    public void writeItemDetails(Set<ItemDetails> itemDetails) {
        itemDetails.forEach(itemDetail -> log.info("{}", itemDetail));
    }

    public List<ItemPrice> getFiveMinutePrices() throws JsonProcessingException {
        var jsonClientResponse = gePricesClient.getFiveMinutePrices();
        var fiveMinutePrices = objectMapper.readTree(jsonClientResponse);
        var itemIdAndPriceMap = fiveMinutePrices.get(DATA);
        var timestamp = fiveMinutePrices.get(TIMESTAMP).asInt();

        var itemPrices = new ArrayList<ItemPrice>();
        itemIdAndPriceMap.fields().forEachRemaining(mapEntry -> {
            var itemPrice = new ItemPrice();
            itemPrice.setId(Integer.parseInt(mapEntry.getKey()));
            itemPrice.setAvgHighPrice(mapEntry.getValue().get(AVG_HIGH_PRICE).asInt());
            itemPrice.setHighPriceVolume(mapEntry.getValue().get(HIGH_PRICE_VOLUME).asInt());
            itemPrice.setAvgLowPrice(mapEntry.getValue().get(AVG_LOW_PRICE).asInt());
            itemPrice.setLowPriceVolume(mapEntry.getValue().get(LOW_PRICE_VOLUME).asInt());
            itemPrice.setTimestamp(timestamp);
            itemPrices.add(itemPrice);
        });
        return itemPrices;
    }
}
