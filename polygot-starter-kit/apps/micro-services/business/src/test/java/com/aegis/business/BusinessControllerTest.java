package com.aegis.business;

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
class BusinessControllerTest {

  @Autowired MockMvc mvc;

  private String account() throws Exception {
    return mvc.perform(
            post("/business/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"ACME\",\"taxId\":\"T1\"}"))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
  }

  @Test
  void accountAndInvoice() throws Exception {
    String accountId = account();
    mvc.perform(get("/business/accounts")).andExpect(status().isOk());
    mvc.perform(
            post("/business/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"accountId\":\""
                        + accountId
                        + "\",\"amount\":100.0,\"currency\":\"USD\",\"dueDate\":\"2026-12-31\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("OPEN"));
    mvc.perform(get("/business/invoices")).andExpect(status().isOk());
  }

  @Test
  void invoiceForUnknownAccountIsNotFound() throws Exception {
    mvc.perform(
            post("/business/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"accountId\":\"00000000-0000-0000-0000-000000000000\",\"amount\":10.0,\"currency\":\"USD\",\"dueDate\":\"2026-12-31\"}"))
        .andExpect(status().isNotFound());
  }
}