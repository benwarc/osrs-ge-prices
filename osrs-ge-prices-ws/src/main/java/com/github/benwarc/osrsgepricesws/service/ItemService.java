package com.github.benwarc.osrsgepricesws.service;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import com.github.benwarc.osrsgepricesws.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final MongoTemplate mongoTemplate;

    public ItemDto getItemByItemId(Long itemId) {
        return itemMapper.documentToDto(
                mongoTemplate.findOne(
                        Query.query(Criteria.where("itemId").is(itemId)),
                        ItemDocument.class));
    }

    public List<ItemDto> getItemsByName(String name) {
        return itemMapper.documentsToDtos(
                mongoTemplate.find(
                        Query.query(Criteria.where("name").regex(".*" + name + ".*", "i")),
                        ItemDocument.class));
    }
}
