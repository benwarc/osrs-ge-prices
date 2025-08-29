package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import com.github.benwarc.osrsgepricesbatch.model.ItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemProcessListener implements ItemProcessListener<Item, ItemModel> {

    @Override
    public void beforeProcess(Item item) {
        log.debug("Preparing to process item {}", item);
    }

    @Override
    public void afterProcess(Item item, ItemModel itemModel) {
        log.debug("Successfully processed item from {} to {}", item, itemModel);
    }

    @Override
    public void onProcessError(Item item, Exception e) {
        log.debug("Exception thrown while processing item {}", item, e);
    }
}
