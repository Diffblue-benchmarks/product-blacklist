package com.sainsburys.gol.productblacklist.blacklist;

import org.springframework.data.repository.CrudRepository;

public interface BlacklistRepository extends CrudRepository<CustomerBlacklistItem, String> {
}
