package com.github.benwarc.osrsgepricesbeans.dto;

public record ItemDto(int id,
                      String name,
                      int value,
                      int limit,
                      int lowalch,
                      int highalch,
                      boolean members) {
}
