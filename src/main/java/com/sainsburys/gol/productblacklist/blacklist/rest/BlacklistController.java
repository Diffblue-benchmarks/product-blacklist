package com.sainsburys.gol.productblacklist.blacklist.rest;

import com.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import com.sainsburys.gol.productblacklist.blacklist.persistence.BlacklistRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blacklist")
@RequiredArgsConstructor
@Api(value="/blacklist", description="Customer blacklist, allows to manage items in it", produces ="application/json")
public class BlacklistController {

    private final BlacklistRepository blacklistRepository;

    private final CustomerBlacklistDTOConverter customerBlacklistDTOConverter;

    @PostMapping(value = "/{customerId}/{sku}")
    @ApiOperation(value = "Add a sku to a customer's blacklist")
    @ApiResponse(code = 201, message = "Created")
    public ResponseEntity<Void> addToBlacklist(
            @ApiParam(value = "The id of the customer to add sku to", required = true) @PathVariable String customerId,
            @ApiParam(value = "The sku to add to the customer", required = true) @PathVariable String sku) {
        CustomerBlacklistItem item = new CustomerBlacklistItem(customerId, sku, new Date());
        blacklistRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{customerId}/{sku}")
    @ApiOperation(value = "Deletes a sku from a customer's  blacklist")
    @ApiResponse(code = 204, message = "Deleted")
    public ResponseEntity<Void> deleteBlacklistItemForCustomer(
            @ApiParam(value = "The id of the customer to delete skus from", required = true) @PathVariable String customerId,
            @ApiParam(value = "The sku to remove from the customer's blacklist", required = true) @PathVariable String sku) {
        blacklistRepository.delete(new CustomerBlacklistItem(customerId, sku,null));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{customerId}")
    @ApiOperation(value = "Gets the list of all skus for a customer's blacklist", response = CustomerBlacklistDTO[].class)
    @ApiResponse(code = 200, message = "OK")
    public List<CustomerBlacklistDTO> getBlackListForCustomer(
            @ApiParam(value = "The id of the customer to retrieve skus", required = true) @PathVariable String customerId) {
        return blacklistRepository.findByPartitionKey(customerId, CustomerBlacklistItem.class)
                .stream()
                .map(customerBlacklistDTOConverter::from)
                .collect(Collectors.toList());
    }
}
