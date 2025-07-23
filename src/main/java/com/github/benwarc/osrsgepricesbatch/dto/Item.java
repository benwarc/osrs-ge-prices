package com.github.benwarc.osrsgepricesbatch.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Item implements Serializable {

    private int id;
    private String name;
    private int value;
    private int limit;
    private int lowalch;
    private int highalch;
    private boolean members;

    private List<Price> prices = new ArrayList<>();
}
