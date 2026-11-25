package com.aegis.security;

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
class SecurityControllerTest {

  @Autowired MockMvc mvc;

  @Test
  void registerTrustRevokeAndFreeze() throws Exception {
    String body = "{\"customerId\":\"c1\",\"fingerprint\":\"fp1\"}";
    String id =
        mvc.perform(post("/devices").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andExpect(jsonPath("$.customerId").value("c1"))
            .andExpect(jsonPath("$.fingerprint").value("fp1"))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(post("/devices/" + id + "/trust"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("TRUSTED"))
        .andExpect(jsonPath("$.trusted").value(true));

    mvc.perform(post("/devices/" + id + "/revoke"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("REVOKED"));

    mvc.perform(get("/devices")).andExpect(status().isOk());

    mvc.perform(post("/freeze/c1")).andExpect(status().isOk());
    mvc.perform(get("/freeze/c1")).andExpect(status().isOk()).andExpect(jsonPath("$").value(true));
  }

  @Test
  void raiseAndConfirmAlert() throws Exception {
    String body = "{\"customerId\":\"c1\",\"amount\":20000.00}";
    String id =
        mvc.perform(post("/alerts").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("OPEN"))
            .andExpect(jsonPath("$.riskScore").value(0.9))
            .andExpect(jsonPath("$.customerId").value("c1"))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    mvc.perform(post("/alerts/" + id + "/confirm"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("CONFIRMED"));

    mvc.perform(get("/alerts")).andExpect(status().isOk());
  }

  @Test
  void unknownDeviceIsNotFound() throws Exception {
    mvc.perform(post("/devices/00000000-0000-0000-0000-000000000000/trust"))
        .andExpect(status().isNotFound());
  }

  @Test
  void negativeAmountIsBadRequest() throws Exception {
    mvc.perform(
            post("/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"c1\",\"amount\":-5.00}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void missingCustomerIdIsBadRequest() throws Exception {
    mvc.perform(
            post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":\"\",\"fingerprint\":\"fp1\"}"))
        .andExpect(status().isBadRequest());
  }
}