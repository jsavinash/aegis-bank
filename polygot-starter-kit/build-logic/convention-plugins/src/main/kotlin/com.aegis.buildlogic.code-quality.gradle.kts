plugins {
    java
    id("com.diffplug.spotless")
    id("com.github.spotbugs")
    id("org.owasp.dependencycheck")
}
spotless { java { palantirJavaFormat() } }
spotbugs { ignoreFailures.set(false) }
