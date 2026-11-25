package com.aegis.gateway;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Registry of upstream routes the gateway proxies to. Configuration, not code, in production. */
public final class RouteRegistry {

  private static final Map<String, String> ROUTES = new LinkedHashMap<>();

  static {
    ROUTES.put("accounts", "http://accounts:8081");
    ROUTES.put("payments", "http://payments:8080");
    ROUTES.put("cards", "http://cards:8082");
    ROUTES.put("billpay", "http://billpay:8083");
    ROUTES.put("lending", "http://lending:8084");
    ROUTES.put("wealth", "http://wealth:8085");
    ROUTES.put("insights", "http://insights:8086");
    ROUTES.put("credit", "http://credit:8087");
    ROUTES.put("security", "http://security:8088");
    ROUTES.put("business", "http://business:8089");
    ROUTES.put("treasury", "http://treasury:8090");
  }

  private RouteRegistry() {}

  public static Map<String, String> all() {
    return Map.copyOf(ROUTES);
  }

  public static List<String> services() {
    return ROUTES.keySet().stream().sorted().toList();
  }
}