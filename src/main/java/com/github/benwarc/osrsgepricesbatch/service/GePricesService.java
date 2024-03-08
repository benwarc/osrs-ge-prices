package com.github.benwarc.osrsgepricesbatch.service;

import com.github.benwarc.osrsgepricesbatch.client.GePricesClient;
import com.github.benwarc.osrsgepricesbatch.dto.ItemDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GePricesService {

    private final GePricesClient gePricesClient;

    private boolean readItemDetails;

    public Set<ItemDetails> readItemDetails() {
        if (!readItemDetails) {
            readItemDetails = true;
            return gePricesClient.getItemDetails();
        } else {
            readItemDetails = false;
            // Adheres to the ItemReader.read contract such that null is returned when all data is exhausted
            return null;
        }
    }

    public void writeItemDetails(Set<ItemDetails> itemDetails) {
        itemDetails.forEach(itemDetail -> log.info("{}", itemDetail));
    }
}
