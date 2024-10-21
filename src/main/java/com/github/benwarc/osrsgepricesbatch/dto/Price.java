package com.github.benwarc.osrsgepricesbatch.dto;

import java.io.Serializable;

public record Price(int itemId,
                    int avgHighPrice,
                    int highPriceVolume,
                    int avgLowPrice,
                    int lowPriceVolume,
                    int timestamp) implements Serializable {
}
