plugins {
    id("java")
}

group = "ru.reosfire.lab8.client"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.reosfire.lab8.client.Main"
    }
}