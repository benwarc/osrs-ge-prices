package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbeans.document.PriceDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceWriteListener implements ItemWriteListener<PriceDocument> {

    @Override
    public void beforeWrite(Chunk<? extends PriceDocument> prices) {
        log.debug("Preparing to write {} prices", prices.size());
    }

    @Override
    public void afterWrite(Chunk<? extends PriceDocument> prices) {
        log.debug("Successfully wrote {}", prices);
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends PriceDocument> prices) {
        log.error("Exception thrown while writing {} prices", prices.size(), e);
    }
}
