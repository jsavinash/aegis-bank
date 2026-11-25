plugins { java }
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }
repositories { mavenCentral() }
tasks.withType<JavaCompile> { options.encoding = "UTF-8" }
dependencyLocking { lockAllConfigurations() }
