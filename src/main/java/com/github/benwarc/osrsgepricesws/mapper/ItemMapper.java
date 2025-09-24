package com.github.benwarc.osrsgepricesws.mapper;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", source = "itemId")
    ItemDto documentToDto(ItemDocument item);
    List<ItemDto> documentsToDtos(List<ItemDocument> items);
}
