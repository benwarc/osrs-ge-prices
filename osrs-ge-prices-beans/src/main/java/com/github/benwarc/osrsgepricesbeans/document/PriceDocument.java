package com.github.benwarc.osrsgepricesbeans.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "prices")
public class PriceDocument {

    @Id
    private String id;
    private int itemId;
    private int avgHighPrice;
    private int highPriceVolume;
    private int avgLowPrice;
    private int lowPriceVolume;
    private int timestamp;
}
