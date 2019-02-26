package co.uk.sainsburys.gol.productblacklist.blacklist.rest;

import co.uk.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerBlacklistDTOConverterTest {

    private CustomerBlacklistDTOConverter converter = new CustomerBlacklistDTOConverter();

    private CustomerBlacklistItem testItemA = new CustomerBlacklistItem("test", "item1", new Date());

    @Test
    public void testCreatesDto() {
        CustomerBlacklistDTO result = converter.from(testItemA);
        assertEquals(testItemA.getCustomerId(), result.getCustomerId());
        assertEquals(testItemA.getSku(), result.getSku());
    }
}
