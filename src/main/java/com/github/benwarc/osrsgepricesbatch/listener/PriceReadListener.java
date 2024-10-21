package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceReadListener implements ItemReadListener<Price> {

    @Override
    public void beforeRead() {
        log.debug("Preparing to read price");
    }

    @Override
    public void afterRead(Price price) {
        log.debug("Successfully read {}", price);
    }

    @Override
    public void onReadError(Exception e) {
        log.error("{} thrown while reading price", e.getCause().toString());
    }
}
