package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbeans.dto.PriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceReadListener implements ItemReadListener<PriceDto> {

    @Override
    public void beforeRead() {
        log.debug("Preparing to read price");
    }

    @Override
    public void afterRead(PriceDto price) {
        log.debug("Successfully read {}", price);
    }

    @Override
    public void onReadError(Exception e) {
        log.error("Exception thrown while reading price", e);
    }
}
