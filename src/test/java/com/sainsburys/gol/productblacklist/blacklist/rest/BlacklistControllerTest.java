package com.sainsburys.gol.productblacklist.blacklist.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.gol.productblacklist.blacklist.model.CustomerBlacklistItem;
import com.sainsburys.gol.productblacklist.blacklist.persistence.BlacklistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BlacklistController.class)
public class BlacklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlacklistRepository blacklistRepository;

    @MockBean
    private CustomerBlacklistDTOConverter customerBlacklistDTOConverter;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String testCustomerId = "9c483fa4-e5b0-4916-9f24-268b9a7544f0";
    private String testSku = "5fe9e818-5021-4077-82cd-c2f39d5320c1";
    private String testSku2 = "49bf7c93-4eb7-4b2f-b391-667529d0a649";

    private CustomerBlacklistItem testItemA = new CustomerBlacklistItem(testCustomerId, testSku, new Date());
    private CustomerBlacklistItem testItemB = new CustomerBlacklistItem(testCustomerId, testSku2, new Date());

    private CustomerBlacklistDTO testItemDtoA = CustomerBlacklistDTO.builder().customerId(testCustomerId).sku(testSku).build();
    private CustomerBlacklistDTO testItemDtoB = CustomerBlacklistDTO.builder().customerId(testCustomerId).sku(testSku2).build();

    @Test
    public void testPOSTMissingPathParamsReturns404() throws Exception {
        mockMvc.perform(post("/blacklist")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testPOSTMissingSkuReturns405() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, ""))).andDo(print()).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testPOSTMissingCustomerIdReturns405() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", "", testSku))).andDo(print()).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testPOSTCorrectUrlReturns201() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, testSku))).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void testPOSTCorrectUrlSavesBlacklistItemWithTimestamp() throws Exception {
        Date baseTime = new Date();

        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, testSku))).andDo(print());

        ArgumentCaptor<CustomerBlacklistItem> captor = ArgumentCaptor.forClass(CustomerBlacklistItem.class);
        verify(blacklistRepository).save(captor.capture());
        CustomerBlacklistItem result = captor.getValue();

        assertEquals(testCustomerId, result.getCustomerId());
        assertEquals(testSku, result.getSku());
        assertTrue(baseTime.before(result.getBlacklistedOn()));
    }

    @Test
    public void testDELETEMissingPathParamsReturns404() throws Exception {
        mockMvc.perform(delete("/blacklist")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testDELETEMissingSkuReturns405() throws Exception {
        mockMvc.perform(delete(String.format("/blacklist/%s/%s", testCustomerId, ""))).andDo(print()).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testDELETEMissingCustomerIdReturns405() throws Exception {
        mockMvc.perform(delete(String.format("/blacklist/%s/%s", "", testSku))).andDo(print()).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testDELETECorrectUrlReturns204() throws Exception {
        mockMvc.perform(delete(String.format("/blacklist/%s/%s", testCustomerId, testSku))).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void testGETMissingPathParamsReturns404() throws Exception {
        mockMvc.perform(get("/blacklist")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testGETReturnsEmptyList() throws Exception {

        when(blacklistRepository.findByPartitionKey("1", CustomerBlacklistItem.class)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/blacklist/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGETReturnsTwoItems() throws Exception {

        List<CustomerBlacklistDTO> expected = Arrays.asList(testItemDtoA, testItemDtoB);
        when(blacklistRepository.findByPartitionKey("1", CustomerBlacklistItem.class)).thenReturn(Arrays.asList(testItemA, testItemB));
        when(customerBlacklistDTOConverter.from(testItemA)).thenReturn(testItemDtoA);
        when(customerBlacklistDTOConverter.from(testItemB)).thenReturn(testItemDtoB);

        mockMvc.perform(get("/blacklist/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}
