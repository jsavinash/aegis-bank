plugins {
    id("com.aegis.buildlogic.java-base")
    id("com.aegis.buildlogic.testing")
    id("com.aegis.buildlogic.code-quality")
    id("io.spring.dependency-management")
}
dependencies { implementation(platform("com.aegis.platforms:spring-boot:1.0.0")) }
