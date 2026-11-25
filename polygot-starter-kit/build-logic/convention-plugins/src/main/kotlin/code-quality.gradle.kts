plugins {
    id("com.diffplug.spotless")
    id("com.github.spotbugs")
    id("org.owasp.dependencycheck")
    id("org.sonarqube")
}
spotless { java { palantirJavaFormat() } }
spotbugs { ignoreFailures.set(false) }
dependencyCheck { nvd { apiKey.set(System.getenv("NVD_API_KEY")) } }
