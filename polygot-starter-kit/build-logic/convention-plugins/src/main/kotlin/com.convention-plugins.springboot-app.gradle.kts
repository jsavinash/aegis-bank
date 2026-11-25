plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.6"
    jacoco
    checkstyle
    pmd
}
repositories { mavenCentral() }
dependencies {
    implementation(platform("com.aegis.platforms:springboot-platform:1.0.0"))
}
tasks.withType<Test> { useJUnitPlatform() }
tasks.jacocoTestReport { reports { xml.required.set(true); html.required.set(true) } }
tasks.check { dependsOn(tasks.jacocoTestCoverageVerification) }
jacoco { toolVersion = "0.8.12" }
