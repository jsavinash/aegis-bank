package com.aegis.insights;

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
class InsightsControllerTest {

  @Autowired MockMvc mvc;

  @Test
  void createGetAndSpend() throws Exception {
    String create =
        "{\"customerId\":\"c1\",\"category\":\"FOOD\",\"limit\":300.0,\"currency\":\"USD\"}";
    String id =
        mvc.perform(post("/budgets").contentType(MediaType.APPLICATION_JSON).content(create))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.category").value("FOOD"))
            .andExpect(jsonPath("$.limit.amount").value(300.0))
            .andExpect(jsonPath("$.currency").value("USD"))
            .andExpect(jsonPath("$.overBudget").value(false))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(
            post("/budgets/" + id + "/spend")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50.0}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.spent.amount").value(50.0))
        .andExpect(jsonPath("$.overBudget").value(false));

    mvc.perform(get("/budgets/" + id)).andExpect(status().isOk());
    mvc.perform(get("/budgets")).andExpect(status().isOk());

    mvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"accountId\":\"a1\",\"category\":\"FOOD\",\"amount\":12.5,\"currency\":\"USD\",\"merchant\":\"Cafe\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.category").value("FOOD"));

    mvc.perform(get("/transactions")).andExpect(status().isOk());
    mvc.perform(get("/transactions/by-category")).andExpect(status().isOk());
  }

  @Test
  void unknownBudgetIsNotFound() throws Exception {
    mvc.perform(get("/budgets/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }
}