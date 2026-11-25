package com.aegis.billpay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class BillControllerTest {
  @Autowired MockMvc mvc;
  @Test void addAndList() throws Exception {
    mvc.perform(post("/bills").contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"c1\",\"payee\":\"ACME\",\"amount\":50.0,\"dueDate\":\"2026-12-01\"}")).andExpect(status().isCreated());
    mvc.perform(get("/bills")).andExpect(status().isOk());
  }
}
