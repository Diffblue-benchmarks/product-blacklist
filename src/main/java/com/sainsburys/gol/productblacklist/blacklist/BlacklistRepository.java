package com.sainsburys.gol.productblacklist.blacklist;

import org.springframework.data.repository.CrudRepository;

interface BlacklistRepository extends CrudRepository<CustomerBlacklistItem, String> {
}
