plugins {
    kotlin("jvm") version "1.8.10"
    `maven-publish`
    `java-gradle-plugin`
}

group = "net.graphitemc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("com.googlecode.java-diff-utils:diffutils:1.3.0")
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