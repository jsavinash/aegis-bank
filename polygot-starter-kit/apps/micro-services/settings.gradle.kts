pluginManagement { includeBuild("../../build-logic") }
rootProject.name = "micro-services"
includeBuild("../../platforms")
include("payments", "api-gateway", "accounts", "cards")
