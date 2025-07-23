package com.github.benwarc.osrsgepricesbatch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prices")
@Data
public class PriceModel {

    @Id
    private String id;
    private int itemId;
    private int avgHighPrice;
    private int highPriceVolume;
    private int avgLowPrice;
    private int lowPriceVolume;
    private int timestamp;
}
