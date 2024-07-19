plugins {
    kotlin("jvm") version "1.9.23"
    `kotlin-dsl`
}

group = "com.github.xeounxzxu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.kotest:kotest-runner-junit5-jvm:${PluginsVersion.kotestVersion}")
    implementation("io.kotest:kotest-assertions-core-jvm:${PluginsVersion.kotestVersion}")
    implementation("io.kotest:kotest-property:${PluginsVersion.kotestVersion}")
    implementation("io.kotest.extensions:kotest-extensions-spring:${PluginsVersion.extensionsSpringVersion}")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
