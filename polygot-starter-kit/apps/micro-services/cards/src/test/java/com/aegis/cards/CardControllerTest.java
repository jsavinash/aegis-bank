package com.aegis.cards;

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
class CardControllerTest {

  @Autowired MockMvc mvc;

  private String issue() throws Exception {
    return mvc.perform(
            post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"type\":\"PHYSICAL_DEBIT\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andReturn()
        .getResponse()
        .getContentAsString()
        .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
  }

  @Test
  void issueFreezeUnfreeze() throws Exception {
    String id = issue();
    mvc.perform(post("/cards/" + id + "/freeze")).andExpect(status().isOk());
    mvc.perform(post("/cards/" + id + "/unfreeze")).andExpect(status().isOk());
    mvc.perform(get("/cards")).andExpect(status().isOk());
  }

  @Test
  void controlsBlockUnauthorizedChannel() throws Exception {
    String id = issue();
    mvc.perform(
            patch("/cards/" + id + "/controls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"international\":false,\"online\":true}"))
        .andExpect(status().isOk());
    mvc.perform(
            post("/cards/" + id + "/authorize")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":5.00,\"channel\":\"INTERNATIONAL\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void authorizeWithinLimitsSucceeds() throws Exception {
    String id = issue();
    mvc.perform(
            post("/cards/" + id + "/authorize")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":5.00,\"channel\":\"ONLINE\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.spentToday").value(5.0));
  }

  @Test
  void invalidTypeIsBadRequest() throws Exception {
    mvc.perform(
            post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"type\":\"WOODEN\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void unknownCardIsNotFound() throws Exception {
    mvc.perform(get("/cards/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }
}