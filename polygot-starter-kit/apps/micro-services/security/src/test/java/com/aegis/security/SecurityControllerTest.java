package com.aegis.security;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class SecurityControllerTest {
  @Autowired MockMvc mvc;
  @Test void registerTrustFreeze() throws Exception {
    mvc.perform(post("/security/devices").param("customerId","c1").param("fingerprint","fp1")).andExpect(status().isOk());
    mvc.perform(post("/security/freeze").param("customerId","c1")).andExpect(status().isOk());
    mvc.perform(get("/security/frozen").param("customerId","c1")).andExpect(jsonPath("$").value(true));
  }
}
