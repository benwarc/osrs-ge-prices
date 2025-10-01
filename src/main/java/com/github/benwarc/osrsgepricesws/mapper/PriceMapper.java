package com.github.benwarc.osrsgepricesws.mapper;

import com.github.benwarc.osrsgepricesbeans.document.PriceDocument;
import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceDto documentToDto(PriceDocument price);
}
