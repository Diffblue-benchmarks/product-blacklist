package com.sainsburys.gol.productblacklist.blacklist.persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.sainsburys.gol.productblacklist.ProductBlacklistApplication;
import com.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import com.sainsburys.gol.productblacklist.common.persistence.DynamoDBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DynamoDBConfig.class, ProductBlacklistApplication.class})
public class BlacklistRepositoryIT {

    @Autowired
    private BlacklistRepository blacklistRepository;

    private CustomerBlacklistItem testItemA = new CustomerBlacklistItem("test", "item1", new Date());
    private CustomerBlacklistItem testItemB = new CustomerBlacklistItem("test", "item2", new Date());
    private CustomerBlacklistItem anotherItem = new CustomerBlacklistItem("another", "item2", new Date());

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithNullCustomerId_WhenSaveItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.save(new CustomerBlacklistItem(null, "something", new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithEmptyCustomerId_WhenSaveItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.save(new CustomerBlacklistItem("", "something", new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithNullSku_WhenSaveItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.save(new CustomerBlacklistItem("id1", null, new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithEmptySku_WhenSaveItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.save(new CustomerBlacklistItem("id1", "", new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithNullCustomerId_WhenDeletetem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.delete(new CustomerBlacklistItem(null, "something", new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithEmptyCustomerId_WhenDeleteItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.delete(new CustomerBlacklistItem("", "something", new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithNullSku_WhenDeleteItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.delete(new CustomerBlacklistItem("id1", null, new Date()));
    }

    @Test(expected = DynamoDBMappingException.class)
    public void givenBlacklistItemWithEmptySku_WhenDeleteItem_ThenThrowDynamoDBMappingException() {
        blacklistRepository.delete(new CustomerBlacklistItem("id1", "", new Date()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullPartitionKey_WhenFindItemByPartitionKey_ThenThrowDynamoDBMappingException() {
        blacklistRepository.findByPartitionKey(null, CustomerBlacklistItem.class);
    }

    @Test(expected = AmazonDynamoDBException.class)
    public void givenEmptyPartitionKey_WhenFindItemByPartitionKey_ThenThrowDynamoDBMappingException() {
        blacklistRepository.findByPartitionKey("", CustomerBlacklistItem.class);
    }

    @Test
    public void givenInexistentItem_WhenFindByPartitionKey_ThenReturnEmptyList() {
        List<CustomerBlacklistItem> result = blacklistRepository.findByPartitionKey("inexistent", CustomerBlacklistItem.class);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenExistingItemWithDifferentKey_WhenFindByPartitionKey_ThenReturnEmptyList() {
        blacklistRepository.save(testItemA);

        List<CustomerBlacklistItem> result = blacklistRepository.findByPartitionKey("inexistent", CustomerBlacklistItem.class);
        assertTrue(result.isEmpty());

        blacklistRepository.delete(testItemA);
    }

    @Test
    public void givenThreeExistingItemsWithDifferentKey_WhenFindByPartitionKey_ThenReturnTwoItems() {
        blacklistRepository.save(testItemA);
        blacklistRepository.save(testItemB);
        blacklistRepository.save(anotherItem);

        List<CustomerBlacklistItem> result = blacklistRepository.findByPartitionKey("test", CustomerBlacklistItem.class);
        assertEquals(2, result.stream().filter(c -> c.getCustomerId().equals(testItemA.getCustomerId())).count());

        blacklistRepository.delete(testItemA);
        blacklistRepository.delete(testItemB);
        blacklistRepository.delete(anotherItem);
    }

    @Test
    public void givenValidBlacklistItem_WhenSaveAndDeleteItem_ThenSaveAndDeleteItem() {
        blacklistRepository.save(testItemA);
        blacklistRepository.delete(testItemA);
        List<CustomerBlacklistItem> result = blacklistRepository.findByPartitionKey(testItemA.getCustomerId(), CustomerBlacklistItem.class);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenValidBlacklistItem_WhenDeleteItemWithSamePartitionButDifferentSort_ThenDeleteNothing() {
        blacklistRepository.save(testItemA);

        blacklistRepository.delete(testItemB);

        List<CustomerBlacklistItem> result = blacklistRepository.findByPartitionKey(testItemA.getCustomerId(), CustomerBlacklistItem.class);
        assertEquals(1, result.size());
        blacklistRepository.delete(testItemA);
    }
}
