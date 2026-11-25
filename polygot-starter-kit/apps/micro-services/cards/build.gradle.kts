plugins { id("com.aegis.buildlogic.spring-boot-application") }
application { mainClass.set("com.aegis.cards.CardsApplication") }
dependencies { implementation("org.springframework.boot:spring-boot-starter-validation") }