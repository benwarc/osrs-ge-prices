package com.github.benwarc.osrsgepricesws.service;

import com.github.benwarc.osrsgepricesbeans.document.PriceDocument;
import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import com.github.benwarc.osrsgepricesws.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final MongoTemplate mongoTemplate;
    private final PriceMapper priceMapper;

    public PriceDto getLatestFiveMinuteAveragePriceByItemId(Long itemId) {
        return priceMapper.documentToDto(
                mongoTemplate.findOne(
                        Query
                                .query(Criteria.where("itemId").is(itemId))
                                .with(Sort.by(Sort.Direction.DESC, "timestamp"))
                                .limit(1),
                        PriceDocument.class));
    }
}
