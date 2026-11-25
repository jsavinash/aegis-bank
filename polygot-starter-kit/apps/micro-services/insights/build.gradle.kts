plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.insights.InsightsApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }