plugins {
    id("java")
}

group = "ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.test {
    useJUnitPlatform()
}