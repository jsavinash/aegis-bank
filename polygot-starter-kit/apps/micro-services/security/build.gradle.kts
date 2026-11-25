plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.security.SecurityApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }