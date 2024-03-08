package com.github.benwarc.osrsgepricesbatch.dto;

public record ItemDetails(int id, String name, int value, int limit, int lowalch, int highalch, boolean members) {
}
