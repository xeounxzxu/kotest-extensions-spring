import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

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
        maven("https://jitpack.io")
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<KotlinJvmProjectExtension>("kotlin") {
        jvmToolchain(PluginsVersion.jvm.toInt())
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(PluginsVersion.jvm))
        }
    }

    dependencies {
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")
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
