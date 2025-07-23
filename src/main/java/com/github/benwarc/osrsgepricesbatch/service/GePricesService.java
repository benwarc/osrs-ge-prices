package com.github.benwarc.osrsgepricesbatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benwarc.osrsgepricesbatch.client.GePricesClient;
import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.dto.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.benwarc.osrsgepricesbatch.configuration.RedisConfiguration.DOUBLE_COLON;
import static com.github.benwarc.osrsgepricesbatch.configuration.RedisConfiguration.ITEM_CACHE;

@Service
@RequiredArgsConstructor
@Slf4j
public class GePricesService {

    private final GePricesClient gePricesClient;
    private final ObjectMapper objectMapper;
    private final RedisCacheManager redisCacheManager;
    private final RedisTemplate<String, Item> redisTemplate;

    private boolean readItemDetails;
    private OffsetDateTime lastReadItemMapping;

    // Five minute prices
    private static final String DATA = "data";
    private static final String AVG_HIGH_PRICE = "avgHighPrice";
    private static final String HIGH_PRICE_VOLUME = "highPriceVolume";
    private static final String AVG_LOW_PRICE = "avgLowPrice";
    private static final String LOW_PRICE_VOLUME = "lowPriceVolume";
    private static final String TIMESTAMP = "timestamp";

    public List<Item> readItemDetails() {
        if (!readItemDetails) {
            readItemDetails = true;

            var itemCache = Objects.requireNonNull(redisCacheManager.getCache(ITEM_CACHE), ITEM_CACHE + " cache must not be null");

            if (lastReadItemMapping == null || OffsetDateTime.now().isAfter(lastReadItemMapping.plusHours(24))) {
                lastReadItemMapping = OffsetDateTime.now();

                List<Item> itemMapping = gePricesClient.getItemMapping().orElse(Collections.emptyList());
                if (!itemMapping.isEmpty()) {
                    itemMapping.forEach(item -> itemCache.putIfAbsent(item.getId(), item));
                }
            }

            Map<Integer, Price> prices = getFiveMinutePrices()
                    .stream()
                    .collect(Collectors.toMap(Price::itemId, Function.identity()));
            prices.forEach((itemId, price) -> {
                var item = itemCache.get(itemId, Item.class);
                if (item != null) {
                    item.getPrices().add(price);
                    itemCache.put(itemId, item);
                }
            });

            var items = new ArrayList<Item>();
            try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions().build())) {
                cursor.forEachRemaining(key -> {
                    items.add(itemCache.get(key.substring((ITEM_CACHE + DOUBLE_COLON).length()), Item.class));
                });
            }
            return items;
        } else {
            readItemDetails = false;
            // Adheres to the ItemReader.read contract such that null is returned when all data is exhausted
            return null;
        }
    }

    public void writeItemDetails(List<Item> itemDetails) {
        itemDetails.forEach(itemDetail -> log.info("{}", itemDetail));
    }

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
