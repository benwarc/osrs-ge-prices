package com.github.benwarc.osrsgepricesbatch.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Item implements Serializable {

    private final int id;
    private final String name;
    private final int value;
    private final int limit;
    private final int lowalch;
    private final int highalch;
    private final boolean members;

    private final List<ItemPrice> prices = new ArrayList<>();
}
