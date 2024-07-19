plugins {
    kotlin("jvm") version "1.9.23"
    `kotlin-dsl`
}

group = "com.github.xeounxzxu"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    val version = PluginsVersion

    implementation("org.springframework.restdocs:spring-restdocs-mockmvc:${version.restdocs}")
    implementation("org.springframework.restdocs:spring-restdocs-restassured:3.0.1")

    implementation("io.kotest:kotest-runner-junit5-jvm:${version.kotest}")
    implementation("io.kotest:kotest-assertions-core-jvm:${version.kotest}")
    implementation("io.kotest:kotest-property:${version.kotest}")
    implementation("io.kotest.extensions:kotest-extensions-spring:${version.extensionsSpring}")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
