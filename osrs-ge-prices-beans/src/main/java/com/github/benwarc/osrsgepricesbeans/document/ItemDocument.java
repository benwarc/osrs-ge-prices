package com.github.benwarc.osrsgepricesbeans.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "items")
public class ItemDocument {

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
