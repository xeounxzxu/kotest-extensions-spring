plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") apply false
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.github.xeounxzxu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {

    val version = PluginsVersion

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//    testImplementation("com.github.xeounxzxu:kotest-extensions-spring-restdocs:0253fa8280")

    // kotest setting
    testImplementation("io.kotest:kotest-runner-junit5-jvm:${version.kotest}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${version.kotest}")
    testImplementation("io.kotest:kotest-property:${version.kotest}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${version.extensionsSpring}")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
