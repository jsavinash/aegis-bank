package com.aegis.credit;

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
class CreditControllerTest {

  @Autowired MockMvc mvc;

  @Test
  void upsertGetAndSimulate() throws Exception {
    String id =
        mvc.perform(
                post("/credit/profiles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"customerId\":\"c1\",\"score\":700}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.customerId").value("c1"))
            .andExpect(jsonPath("$.score").value(700))
            .andExpect(jsonPath("$.band").value("GOOD"))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(get("/credit/profiles/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerId").value("c1"))
        .andExpect(jsonPath("$.score").value(700));

    mvc.perform(get("/credit/profiles")).andExpect(status().isOk());

    mvc.perform(get("/credit/profiles/simulate").param("base", "700").param("delta", "-50"))
        .andExpect(status().isOk())
        .andExpect(content().string("650"));
  }

  @Test
  void invalidScoreIsBadRequest() throws Exception {
    mvc.perform(
            post("/credit/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"score\":900}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void unknownProfileIsNotFound() throws Exception {
    mvc.perform(get("/credit/profiles/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }
}