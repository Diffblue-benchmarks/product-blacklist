package co.uk.sainsburys.gol.productblacklist.blacklist.rest;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class CustomerBlacklistDTO {
    private final String customerId;
    private final String sku;
}
