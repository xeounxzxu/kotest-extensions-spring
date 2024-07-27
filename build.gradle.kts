plugins {
    val kotlinVersion = PluginsVersion.kotlin
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
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

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }
}

