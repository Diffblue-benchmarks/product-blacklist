package com.sainsburys.gol.productblacklist.blacklist.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomerBlacklistId {
    private String customerId;
    private String sku;

    @DynamoDBHashKey(attributeName = "customerId")
    public String getCustomerId() {
        return customerId;
    }

    @SuppressWarnings("unused")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBRangeKey(attributeName = "sku")
    public String getSku() {
        return sku;
    }

    @SuppressWarnings("unused")
    public void setSku(String sku) {
        this.sku = sku;
    }
}
