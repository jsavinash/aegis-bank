package com.aegis.treasury;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TreasuryControllerTest {

  private static final String ACCOUNT = "00000000-0000-0000-0000-000000000001";

  @Autowired MockMvc mvc;

  @Test
  void setGetCreditDebitForecast() throws Exception {
    mvc.perform(
            put("/treasury/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"" + ACCOUNT + "\",\"balance\":1000.00,\"currency\":\"USD\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/treasury/positions/" + ACCOUNT))
        .andExpect(jsonPath("$.accountId").value(ACCOUNT))
        .andExpect(jsonPath("$.balance.amount").value(1000.0))
        .andExpect(jsonPath("$.currency").value("USD"));

    mvc.perform(get("/treasury/positions")).andExpect(status().isOk());
    mvc.perform(get("/treasury/positions/" + ACCOUNT)).andExpect(status().isOk());

    mvc.perform(
            post("/treasury/positions/" + ACCOUNT + "/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":250.00}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance.amount").value(1250.0));

    mvc.perform(
            post("/treasury/positions/" + ACCOUNT + "/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50.00}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance.amount").value(1200.0));

    mvc.perform(get("/treasury/positions/" + ACCOUNT + "/forecast").param("weeks", "13"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.weeks").value(13))
        .andExpect(jsonPath("$.projectedBalance").value(15600.0));
  }

  @Test
  void updateExistingReturnsOk() throws Exception {
    mvc.perform(
            put("/treasury/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"" + ACCOUNT + "\",\"balance\":10.00,\"currency\":\"USD\"}"))
        .andExpect(status().isCreated());
    mvc.perform(
            put("/treasury/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"" + ACCOUNT + "\",\"balance\":20.00,\"currency\":\"USD\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance.amount").value(20.0));
  }

  @Test
  void unknownPositionIsNotFound() throws Exception {
    mvc.perform(get("/treasury/positions/00000000-0000-0000-0000-000000000099"))
        .andExpect(status().isNotFound());
  }

  @Test
  void overdraftIsConflict() throws Exception {
    mvc.perform(
            put("/treasury/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"" + ACCOUNT + "\",\"balance\":5.00,\"currency\":\"USD\"}"))
        .andExpect(status().isCreated());
    mvc.perform(
            post("/treasury/positions/" + ACCOUNT + "/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100.00}"))
        .andExpect(status().isConflict());
  }

  @Test
  void missingFieldsAreBadRequest() throws Exception {
    mvc.perform(
            put("/treasury/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\":5.00}"))
        .andExpect(status().isBadRequest());
  }
}