package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemWriteListener implements ItemWriteListener<ItemDocument> {

    @Override
    public void beforeWrite(Chunk<? extends ItemDocument> items) {
        log.debug("Preparing to write {} items", items.size());
    }

    @Override
    public void afterWrite(Chunk<? extends ItemDocument> items) {
        log.debug("Successfully wrote {}", items);
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends ItemDocument> items) {
        log.error("Exception thrown while writing {} items", items.size(), e);
    }
}
