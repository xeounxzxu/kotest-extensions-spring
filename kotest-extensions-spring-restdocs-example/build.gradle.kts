plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") apply false
    kotlin("plugin.spring") version "1.9.24"
    id("org.asciidoctor.jvm.convert") version "3.3.2" // SETTING TO SPRING
}

group = "com.github.xeounxzxu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// SETTING TO SPRING
configurations {
    create("asciidoctorExt")
}
 dependencies {

    val version = PluginsVersion

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("com.github.xeounxzxu:kotest-extensions-spring-restdocs:v1.0.0")

    // kotest setting
    testImplementation("io.kotest:kotest-runner-junit5-jvm:${version.kotest}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${version.kotest}")
    testImplementation("io.kotest:kotest-property:${version.kotest}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${version.extensionsSpring}")

    // SETTING TO SPRING
    "asciidoctorExt"("org.springframework.restdocs:spring-restdocs-asciidoctor:3.0.1")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

// SETTING TO SPRING
val snippetsDir by extra { file("build/generated-snippets") }

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.named<org.asciidoctor.gradle.jvm.AsciidoctorTask>("asciidoctor") {
    inputs.dir(snippetsDir)
    configurations("asciidoctorExt")
    dependsOn(tasks.test)
}

tasks.withType<Test> {
    // SETTING TO SPRING
    outputs.dir(snippetsDir)
    useJUnitPlatform()
}
