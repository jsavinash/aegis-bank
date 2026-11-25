package com.aegis.billpay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BillControllerTest {

  @Autowired MockMvc mvc;

  private String add() throws Exception {
    return mvc.perform(
            post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"customerId\":\"c1\",\"payee\":\"ACME\",\"amount\":50.0,\"currency\":\"USD\",\"dueDate\":\"2026-12-01\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("SCHEDULED"))
        .andReturn()
        .getResponse()
        .getContentAsString()
        .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
  }

  @Test
  void addAndList() throws Exception {
    add();
    mvc.perform(get("/bills")).andExpect(status().isOk());
  }

  @Test
  void autopayThenPay() throws Exception {
    String id = add();
    mvc.perform(post("/bills/" + id + "/autopay")).andExpect(status().isOk());
    mvc.perform(post("/bills/" + id + "/pay")).andExpect(status().isOk());
  }

  @Test
  void missingCurrencyIsBadRequest() throws Exception {
    mvc.perform(
            post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"payee\":\"ACME\",\"amount\":50.0,\"dueDate\":\"2026-12-01\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void unknownBillIsNotFound() throws Exception {
    mvc.perform(get("/bills/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }
}