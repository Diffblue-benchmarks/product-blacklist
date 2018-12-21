package com.sainsburys.gol.productblacklist.blacklist;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@DynamoDBTable(tableName = "CustomerBlacklist")
@NoArgsConstructor
public class CustomerBlacklistItem {
    @Id
    private CustomerBlacklistId id;
    private Date blacklistedOn;

    public CustomerBlacklistItem(String customerId, String sku, Date blacklistedOn) {
        this.id = new CustomerBlacklistId(customerId, sku);
        this.blacklistedOn = blacklistedOn;
    }

    @DynamoDBHashKey(attributeName = "customerId")
    public String getCustomerId() {
        return id.getCustomerId();
    }

    @DynamoDBRangeKey(attributeName = "sku")
    public String getSku() {
        return id.getSku();
    }

    public Date getBlacklistedOn() {
        return blacklistedOn;
    }

    public void setCustomerId(String customerId) {
        this.id = new CustomerBlacklistId(customerId, id.getSku());
    }

    public void setBlacklistedOn(Date blacklistedOn) {
        this.blacklistedOn = blacklistedOn;
    }

    public void setSku(String sku) {
        this.id = new CustomerBlacklistId(id.getCustomerId(), sku);
    }
}
