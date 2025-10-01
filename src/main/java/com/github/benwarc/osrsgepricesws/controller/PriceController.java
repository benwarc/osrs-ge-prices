package com.github.benwarc.osrsgepricesws.controller;

import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import com.github.benwarc.osrsgepricesws.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/prices/item-id/{item-id}")
    public PriceDto getLatestFiveMinuteAveragePriceByItemId(@PathVariable("item-id") Long itemId) {
        return priceService.getLatestFiveMinuteAveragePriceByItemId(itemId);
    }
}
