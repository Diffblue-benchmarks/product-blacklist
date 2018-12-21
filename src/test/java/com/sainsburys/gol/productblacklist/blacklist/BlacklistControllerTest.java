package com.sainsburys.gol.productblacklist.blacklist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BlacklistController.class)
public class BlacklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlacklistRepository blacklistRepository;

    private String testCustomerId = "9c483fa4-e5b0-4916-9f24-268b9a7544f0";
    private String testSku = "5fe9e818-5021-4077-82cd-c2f39d5320c1";

    @Test
    public void testMissingPathParamsReturns404() throws Exception {
        mockMvc.perform(post("/blacklist")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testMissingSkuReturns404() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, ""))).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testMissingCustomerIdReturns404() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", "", testSku))).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testCorrectUrlReturns201() throws Exception {
        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, testSku))).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void testCorrectUrlSavesBlacklistItemWithTimestamp() throws Exception {
        Date baseTime = new Date();

        mockMvc.perform(post(String.format("/blacklist/%s/%s", testCustomerId, testSku))).andDo(print());

        ArgumentCaptor<CustomerBlacklistItem> captor = ArgumentCaptor.forClass(CustomerBlacklistItem.class);
        verify(blacklistRepository).save(captor.capture());
        CustomerBlacklistItem result = captor.getValue();

        assertEquals(testCustomerId, result.getCustomerId());
        assertEquals(testSku, result.getSku());
        assertTrue(baseTime.before(result.getBlacklistedOn()));
    }
}
