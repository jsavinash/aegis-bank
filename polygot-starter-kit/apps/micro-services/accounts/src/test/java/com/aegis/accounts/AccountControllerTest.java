package com.aegis.accounts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

  @Autowired MockMvc mvc;

  @Test
  void openGetDepositWithdraw() throws Exception {
    String open = "{\"customerId\":\"c1\",\"type\":\"CHECKING\",\"currency\":\"USD\"}";
    String id =
        mvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(open))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andExpect(jsonPath("$.balance.amount").value(0.0))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(
            post("/accounts/" + id + "/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100.00,\"idempotencyKey\":\"d1\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance.amount").value(100.0));

    mvc.perform(
            post("/accounts/" + id + "/withdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":40.00,\"idempotencyKey\":\"w1\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance.amount").value(60.0));

    mvc.perform(get("/accounts/" + id)).andExpect(status().isOk());
    mvc.perform(get("/accounts")).andExpect(status().isOk());
  }

  @Test
  void invalidTypeIsBadRequest() throws Exception {
    mvc.perform(
            post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"type\":\"NOPE\",\"currency\":\"USD\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void unknownAccountIsNotFound() throws Exception {
    mvc.perform(get("/accounts/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }

  @Test
  void overdraftIsConflict() throws Exception {
    String open = "{\"customerId\":\"c2\",\"type\":\"SAVINGS\",\"currency\":\"USD\"}";
    String id =
        mvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(open))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
    mvc.perform(
            post("/accounts/" + id + "/withdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":10.00,\"idempotencyKey\":\"w1\"}"))
        .andExpect(status().isConflict());
  }

  @Test
  void healthIsOk() throws Exception {
    mvc.perform(get("/health")).andExpect(status().isOk()).andExpect(jsonPath("$.ok").value(true));
  }
}