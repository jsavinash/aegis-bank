package com.aegis.lending;

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
class LoanControllerTest {

  @Autowired MockMvc mvc;

  @Test
  void applyApproveAndGet() throws Exception {
    String apply = "{\"customerId\":\"c1\",\"type\":\"PERSONAL\",\"principal\":5000.00,\"currency\":\"USD\"}";
    String body =
        mvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(apply))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("UNDERWRITING"))
            .andExpect(jsonPath("$.type").value("PERSONAL"))
            .andExpect(jsonPath("$.principal.amount").value(5000.0))
            .andReturn()
            .getResponse()
            .getContentAsString();
    String id = body.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(post("/loans/" + id + "/approve"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("APPROVED"));

    mvc.perform(get("/loans/" + id)).andExpect(status().isOk());
    mvc.perform(get("/loans")).andExpect(status().isOk());
  }

  @Test
  void invalidTypeIsBadRequest() throws Exception {
    mvc.perform(
            post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"customerId\":\"c1\",\"type\":\"NOPE\",\"principal\":100.00,\"currency\":\"USD\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void unknownLoanIsNotFound() throws Exception {
    mvc.perform(get("/loans/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }
}