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
  void openAndGet() throws Exception {
    mvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
            .content("{\"customerId\":\"c1\",\"type\":\"CHECKING\",\"currency\":\"USD\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("ACTIVE"));
    mvc.perform(get("/accounts")).andExpect(status().isOk());
  }
}
