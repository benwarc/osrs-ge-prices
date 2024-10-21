package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbatch.dto.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceWriteListener implements ItemWriteListener<Price> {

    @Override
    public void beforeWrite(Chunk<? extends Price> prices) {
        log.debug("Preparing to write {} prices", prices.size());
    }

    @Override
    public void afterWrite(Chunk<? extends Price> prices) {
        log.debug("Successfully wrote {}", prices);
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends Price> prices) {
        log.error("{} thrown while writing {} prices", e.getCause().toString(), prices.size());
    }
}
