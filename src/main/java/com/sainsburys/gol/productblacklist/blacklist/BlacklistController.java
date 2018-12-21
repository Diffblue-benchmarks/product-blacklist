package com.sainsburys.gol.productblacklist.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistRepository blacklistRepository;

    @PostMapping(value = "blacklist/{customerId}/{sku}")
    public ResponseEntity<Void> addToBlacklist(@PathVariable String customerId, @PathVariable String sku) {
        CustomerBlacklistItem item = new CustomerBlacklistItem(customerId, sku, new Date());
        blacklistRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
