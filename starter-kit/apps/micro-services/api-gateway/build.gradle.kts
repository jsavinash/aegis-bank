plugins { application }
repositories { mavenCentral() }
dependencies { implementation("org.springframework.boot:spring-boot-starter-web:3.3.0") }
application { mainClass.set("com.aegis.gateway.GatewayApplication") }
