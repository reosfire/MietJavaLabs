plugins {
    id("java")
}

group = "ru.reosfire.special"
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
        attributes["Main-Class"] = "ru.reosfire.special.Main"
    }
}