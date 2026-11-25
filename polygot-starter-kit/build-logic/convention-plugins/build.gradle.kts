plugins { `kotlin-dsl` }
group = "com.aegis.buildlogic"
version = "1.0.0"
repositories { mavenCentral() }
dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.6")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.1.2")
    implementation("org.owasp:dependency-check-gradle:12.1.0")
    implementation("org.sonarqube:sonar-gradle-plugin:6.1.0.5364")
    implementation("com.google.cloud.tools:jib-gradle-plugin:3.4.5")
}
