plugins {
    id("com.aegis.buildlogic.java-base")
    id("com.aegis.buildlogic.testing")
    id("com.aegis.buildlogic.code-quality")
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.cloud.tools.jib")
}
dependencies { implementation(platform("com.aegis.platforms:spring-boot-platform:1.0.0")) }
