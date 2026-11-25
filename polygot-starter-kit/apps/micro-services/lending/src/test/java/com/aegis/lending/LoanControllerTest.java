package com.aegis.lending;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class LoanControllerTest {
  @Autowired MockMvc mvc;
  @Test void applyAndDecide() throws Exception {
    mvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"principal\":5000.0,\"currency\":\"USD\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("UNDERWRITING"));
    mvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"principal\":5000.0,\"currency\":\"USD\"}")).andExpect(status().isCreated());
    mvc.perform(get("/loans")).andExpect(status().isOk());
  }
}
