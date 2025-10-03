package com.github.benwarc.osrsgepricesbeans.dto;

public record PriceDto(int itemId,
                       int avgHighPrice,
                       int highPriceVolume,
                       int avgLowPrice,
                       int lowPriceVolume,
                       int timestamp) {
}
