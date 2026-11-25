plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.wealth.WealthApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }