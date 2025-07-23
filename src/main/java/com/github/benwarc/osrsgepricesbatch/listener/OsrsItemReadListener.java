package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemReadListener implements ItemReadListener<Item> {

    @Override
    public void beforeRead() {
        log.debug("Preparing to read item");
    }

    @Override
    public void afterRead(Item item) {
        log.debug("Successfully read {}", item);
    }

    @Override
    public void onReadError(Exception e) {
        log.error("Exception thrown while reading item", e);
    }
}
