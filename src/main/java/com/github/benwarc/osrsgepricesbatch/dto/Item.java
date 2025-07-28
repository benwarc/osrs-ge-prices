package com.github.benwarc.osrsgepricesbatch.dto;

import java.io.Serializable;

public record Item(int id,
                   String name,
                   int value,
                   int limit,
                   int lowalch,
                   int highalch,
                   boolean members) implements Serializable {
}
