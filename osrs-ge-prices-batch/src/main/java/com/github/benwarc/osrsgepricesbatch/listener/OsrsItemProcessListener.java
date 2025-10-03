package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbeans.document.ItemDocument;
import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemProcessListener implements ItemProcessListener<ItemDto, ItemDocument> {

    @Override
    public void beforeProcess(ItemDto item) {
        log.debug("Preparing to process item {}", item);
    }

    @Override
    public void afterProcess(ItemDto itemDto, ItemDocument itemDocument) {
        log.debug("Successfully processed item from {} to {}", itemDto, itemDocument);
    }

    @Override
    public void onProcessError(ItemDto item, Exception e) {
        log.debug("Exception thrown while processing item {}", item, e);
    }
}
