package com.github.benwarc.osrsgepricesbatch.dto;

import java.io.Serializable;

public record ItemPrice(int id,
                        int avgHighPrice,
                        int highPriceVolume,
                        int avgLowPrice,
                        int lowPriceVolume,
                        int timestamp) implements Serializable {
}
