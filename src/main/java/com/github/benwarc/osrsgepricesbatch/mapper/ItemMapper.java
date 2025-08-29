package com.github.benwarc.osrsgepricesbatch.mapper;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemId", source = "id")
    ItemModel dtoToModel(Item item);
}
