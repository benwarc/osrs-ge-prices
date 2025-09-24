package com.github.benwarc.osrsgepricesws.controller;

import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import com.github.benwarc.osrsgepricesws.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/{name}")
    public List<ItemDto> getItemsByName(@PathVariable String name) {
        return itemService.getItemsByName(name);
    }
}
