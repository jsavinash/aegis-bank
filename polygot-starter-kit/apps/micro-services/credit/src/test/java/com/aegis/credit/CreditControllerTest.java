package com.aegis.credit;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class CreditControllerTest {
  @Autowired MockMvc mvc;
  @Test void upsertAndSimulate() throws Exception {
    mvc.perform(post("/credit/profiles").param("customerId","c1").param("score","700")).andExpect(status().isCreated()).andExpect(jsonPath("$.band").value("GOOD"));
    mvc.perform(get("/credit/profiles")).andExpect(status().isOk());
    mvc.perform(get("/credit/simulate").param("base","700").param("delta","-50")).andExpect(status().isOk());
  }
}
