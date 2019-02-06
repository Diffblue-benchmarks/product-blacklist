package co.uk.sainsburys.gol.productblacklist.blacklist.rest;

import co.uk.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import org.springframework.stereotype.Component;

@Component
public class CustomerBlacklistDTOConverter {
    public CustomerBlacklistDTO from(CustomerBlacklistItem item) {
        return CustomerBlacklistDTO.builder()
                .customerId(item.getCustomerId())
                .sku(item.getSku())
                .build();
    }
}
