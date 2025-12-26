plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.14.6206")
}

tasks.test {
    useJUnitPlatform()
}