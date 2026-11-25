package com.aegis.wealth;

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
class WealthControllerTest {

  @Autowired MockMvc mvc;

  private static final String GOAL_JSON =
      "{\"customerId\":\"c1\",\"name\":\"Vacation\",\"target\":1000.00,\"currency\":\"USD\"}";

  @Test
  void createGetContributeCancel() throws Exception {
    String id =
        mvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content(GOAL_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Vacation"))
            .andExpect(jsonPath("$.target.amount").value(1000.0))
            .andExpect(jsonPath("$.currency").value("USD"))
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(get("/goals/" + id)).andExpect(status().isOk());
    mvc.perform(get("/goals")).andExpect(status().isOk());

    mvc.perform(
            post("/goals/" + id + "/contribute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":1000.00}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("COMPLETED"));
  }

  @Test
  void contributeAfterCompleteIsConflict() throws Exception {
    String id =
        mvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content(GOAL_JSON))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(
            post("/goals/" + id + "/contribute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":1000.00}"))
        .andExpect(status().isOk());

    mvc.perform(
            post("/goals/" + id + "/contribute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":10.00}"))
        .andExpect(status().isConflict());
  }

  @Test
  void cancelGoalIsOk() throws Exception {
    String id =
        mvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content(GOAL_JSON))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(post("/goals/" + id + "/cancel"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("CANCELLED"));
  }

  @Test
  void unknownGoalIsNotFound() throws Exception {
    mvc.perform(get("/goals/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }

  @Test
  void invalidGoalIsBadRequest() throws Exception {
    mvc.perform(
            post("/goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"name\":\"\",\"target\":100.0,\"currency\":\"USD\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void portfolioCreateAndAddHolding() throws Exception {
    String id =
        mvc.perform(
                post("/portfolios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"customerId\":\"c1\",\"currency\":\"USD\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.currency").value("USD"))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(
            post("/portfolios/" + id + "/holdings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symbol\":\"AAPL\",\"value\":100.00}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.value.amount").value(100.0));

    mvc.perform(get("/portfolios/" + id)).andExpect(status().isOk());
    mvc.perform(get("/portfolios")).andExpect(status().isOk());
  }
}