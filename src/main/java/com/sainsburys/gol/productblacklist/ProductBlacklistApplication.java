package com.sainsburys.gol.productblacklist;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamoDBRepositories
public class ProductBlacklistApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductBlacklistApplication.class, args);
	}
}

