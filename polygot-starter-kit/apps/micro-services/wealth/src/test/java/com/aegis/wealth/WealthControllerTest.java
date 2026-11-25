package com.aegis.wealth;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class WealthControllerTest {
  @Autowired MockMvc mvc;
  @Test void createAndContribute() throws Exception {
    mvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"name\":\"Vacation\",\"target\":1000.0,\"currency\":\"USD\"}")).andExpect(status().isCreated());
    mvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"name\":\"Vacation\",\"target\":1000.0,\"currency\":\"USD\"}")).andExpect(status().isCreated());
    mvc.perform(get("/goals")).andExpect(status().isOk());
  }
}
