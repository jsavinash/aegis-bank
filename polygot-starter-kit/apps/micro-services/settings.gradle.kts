pluginManagement { includeBuild("../../build-logic") }
rootProject.name = "micro-services"
includeBuild("../../platforms")
include("payments", "api-gateway", "accounts", "cards", "billpay", "lending", "wealth", "insights", "credit", "security", "business")
