package com.sainsburys.gol.productblacklist.blacklist.rest;

import com.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import com.sainsburys.gol.productblacklist.blacklist.persistence.BlacklistRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Timed
public class BlacklistController {

    private final BlacklistRepository blacklistRepository;

    private final CustomerBlacklistDTOConverter customerBlacklistDTOConverter;

    @PostMapping(value = "blacklist/{customerId}/{sku}")
    public ResponseEntity<Void> addToBlacklist(@PathVariable String customerId, @PathVariable String sku) {
        CustomerBlacklistItem item = new CustomerBlacklistItem(customerId, sku, new Date());
        blacklistRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "blacklist/{customerId}/{sku}")
    public ResponseEntity<Void> deleteBlacklistItemForCustomer(@PathVariable String customerId, @PathVariable String sku) {
        blacklistRepository.delete(new CustomerBlacklistItem(customerId, sku, null));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "blacklist/{customerId}")
    public List<CustomerBlacklistDTO> getBlackListForCustomer(@PathVariable String customerId) {
        return blacklistRepository.findByPartitionKey(customerId, CustomerBlacklistItem.class)
                .stream()
                .map(customerBlacklistDTOConverter::from)
                .collect(Collectors.toList());
    }
}
