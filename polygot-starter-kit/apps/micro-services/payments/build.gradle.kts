plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.payments.PaymentsApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }