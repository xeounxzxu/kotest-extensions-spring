plugins {
    val version = PluginsVersion
    id("org.jlleitschuh.gradle.ktlint") version version.ktlint
    kotlin("jvm") version version.kotlin
    kotlin("kapt") version version.kotlin
    `kotlin-dsl`
}

allprojects {
    group = "com.github.xeounxzxu"
    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}
