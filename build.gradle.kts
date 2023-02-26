plugins {
    kotlin("jvm") version "1.8.10"
    `maven-publish`
    `java-gradle-plugin`
}

group = "net.graphitemc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.minecraftforge.net")
}

dependencies {
    implementation("net.minecraftforge:DiffPatch:2.0.7:all")
}

gradlePlugin {
    plugins {
        create("net.graphitemc.patcher") {
            id = "net.graphitemc.patcher"
            implementationClass = "net.graphitemc.patcher.PatcherPlugin"
        }
    }
}

kotlin {
    jvmToolchain(17)
}