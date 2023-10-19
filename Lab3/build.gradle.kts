plugins {
    id("java")
}

group = "ru.reosfire.lab3"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.reosfire.lab3.Main"
    }
}