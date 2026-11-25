tasks.register("qualityCheck") { dependsOn(gradle.includedBuilds.map { it.task(":qualityCheck") }) }
tasks.register("fullBuild") { dependsOn("build", "qualityCheck") }
