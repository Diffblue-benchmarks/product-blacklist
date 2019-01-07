package com.sainsburys.gol.productblacklist.common.persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class DynamoRepository<T> {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(T item) {
        dynamoDBMapper.save(item);
    }

    public void delete(T item) {
        dynamoDBMapper.delete(item);
    }

    public List<T> findByPartitionKey(String hash, Class<T> clazz) {
        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>().withHashKeyValues(getHashKObject(hash));
        return dynamoDBMapper.query(clazz, queryExpression);
    }

    public abstract T getHashKObject(String hashKey);
}
