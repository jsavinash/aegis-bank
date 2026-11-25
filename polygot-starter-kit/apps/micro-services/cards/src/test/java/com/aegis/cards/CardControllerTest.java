package com.aegis.cards;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class CardControllerTest {
  @Autowired MockMvc mvc;
  @Test void issueFreeze() throws Exception {
    mvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"type\":\"DEBIT\"}")).andExpect(status().isCreated());
    mvc.perform(get("/cards")).andExpect(status().isOk());
  }
}
