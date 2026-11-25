package com.aegis.business;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc
class BusinessControllerTest {
  @Autowired MockMvc mvc;
  @Test void accountAndInvoice() throws Exception {
    mvc.perform(post("/business/accounts").param("name","ACME").param("taxId","T1")).andExpect(status().isOk());
    mvc.perform(get("/business/accounts")).andExpect(status().isOk());
    mvc.perform(get("/business/invoices")).andExpect(status().isOk());
  }
}
