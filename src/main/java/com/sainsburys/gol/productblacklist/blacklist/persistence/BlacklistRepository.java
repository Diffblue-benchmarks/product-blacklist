package com.sainsburys.gol.productblacklist.blacklist.persistence;

import com.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import com.sainsburys.gol.productblacklist.common.persistence.DynamoRepository;
import org.springframework.stereotype.Component;

@Component
public class BlacklistRepository extends DynamoRepository<CustomerBlacklistItem> {
    @Override
    public CustomerBlacklistItem getHashKObject(String hashKey) {
        return new CustomerBlacklistItem(hashKey, null, null);
    }
}
