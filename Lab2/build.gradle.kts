plugins {
    id("java")
}

group = "ru.reosfire.lab2"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.reosfire.lab2.Main"
    }
}