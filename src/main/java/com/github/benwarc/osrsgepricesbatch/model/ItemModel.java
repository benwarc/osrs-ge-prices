package com.github.benwarc.osrsgepricesbatch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
@Data
public class ItemModel {

    @Id
    private String id;
    private int itemId;
    private String name;
    private int value;
    private int limit;
    private int lowalch;
    private int highalch;
    private boolean members;
}
