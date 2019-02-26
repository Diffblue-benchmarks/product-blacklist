package co.uk.sainsburys.gol.productblacklist.blacklist.persistence;

import co.uk.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BlacklistRepositoryTest {
    private BlacklistRepository blacklistRepository = new BlacklistRepository();

    @Test
    public void testGetHashObjectReturnsValidObject() {
        String key = "validkey";
        CustomerBlacklistItem keyObject = blacklistRepository.getHashKObject(key);
        assertEquals(new CustomerBlacklistItem(key, null, null), keyObject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentExceptionIfNoCustomerKey() {
        blacklistRepository.getHashKObject(null);
    }
}
