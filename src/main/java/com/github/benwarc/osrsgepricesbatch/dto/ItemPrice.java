package com.github.benwarc.osrsgepricesbatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPrice {

    private int id;
    private int avgHighPrice;
    private int highPriceVolume;
    private int avgLowPrice;
    private int lowPriceVolume;
    private int timestamp;
}
