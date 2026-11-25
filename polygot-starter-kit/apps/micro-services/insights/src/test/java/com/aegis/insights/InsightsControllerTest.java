package com.aegis.insights;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class InsightsControllerTest {
  @Autowired MockMvc mvc;
  @Test void createAndSpend() throws Exception {
    mvc.perform(post("/budgets").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"category\":\"FOOD\",\"limit\":300.0}")).andExpect(status().isCreated());
    mvc.perform(get("/budgets")).andExpect(status().isOk());
  }
}
