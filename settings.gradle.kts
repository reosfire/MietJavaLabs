rootProject.name = "MietJavaLabs"
include("Lab2")
include("Lab3")
include("Lab5")
include("Lab7")
include("Lab8:Client")
include("Lab8:Server")
include("Lab8:Common")
findProject(":Lab8:Common")?.name = "Common"
