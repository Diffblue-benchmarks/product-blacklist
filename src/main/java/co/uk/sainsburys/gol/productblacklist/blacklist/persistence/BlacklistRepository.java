package co.uk.sainsburys.gol.productblacklist.blacklist.persistence;

import co.uk.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import co.uk.sainsburys.gol.productblacklist.common.persistence.DynamoRepository;
import org.springframework.stereotype.Component;

@Component
public class BlacklistRepository extends DynamoRepository<CustomerBlacklistItem> {
    @Override
    public CustomerBlacklistItem getHashKObject(String hashKey) {
        if (hashKey == null) throw new IllegalArgumentException("Hashkey cannot be null");
        return new CustomerBlacklistItem(hashKey, null, null);
    }
}
