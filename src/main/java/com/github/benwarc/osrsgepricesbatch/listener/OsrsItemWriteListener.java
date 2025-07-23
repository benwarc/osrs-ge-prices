package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemWriteListener implements ItemWriteListener<ItemModel> {

    @Override
    public void beforeWrite(Chunk<? extends ItemModel> items) {
        log.debug("Preparing to write {} items", items.size());
    }

    @Override
    public void afterWrite(Chunk<? extends ItemModel> items) {
        log.debug("Successfully wrote {}", items);
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends ItemModel> items) {
        log.error("Exception thrown while writing {} items", items.size(), e);
    }
}
