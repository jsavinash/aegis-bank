package com.aegis.payments;

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
class PaymentControllerTest {
  @Autowired MockMvc mvc;

  @Test
  void initiateIsIdempotent() throws Exception {
    String body = "{\"idempotencyKey\":\"k1\",\"fromAccount\":\"a1\",\"toAccount\":\"a2\",\"amount\":10.0,\"currency\":\"USD\",\"type\":\"DOMESTIC\"}";
    mvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("PENDING"));
    mvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isCreated());
    mvc.perform(get("/payments")).andExpect(status().isOk());
  }
}
