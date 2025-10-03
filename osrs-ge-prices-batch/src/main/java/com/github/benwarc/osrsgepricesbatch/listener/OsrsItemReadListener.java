package com.github.benwarc.osrsgepricesbatch.listener;

import com.github.benwarc.osrsgepricesbeans.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsrsItemReadListener implements ItemReadListener<ItemDto> {

    @Override
    public void beforeRead() {
        log.debug("Preparing to read item");
    }

    @Override
    public void afterRead(ItemDto item) {
        log.debug("Successfully read {}", item);
    }

    @Override
    public void onReadError(Exception e) {
        log.error("Exception thrown while reading item", e);
    }
}
