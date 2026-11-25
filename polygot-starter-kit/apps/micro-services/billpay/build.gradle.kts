plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.billpay.BillPayApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }