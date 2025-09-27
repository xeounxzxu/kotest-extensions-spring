plugins {
    id("org.springframework.boot") version PluginsVersion.springBoot
    id("io.spring.dependency-management") version PluginsVersion.springDependencyManagement
    kotlin("plugin.spring") version PluginsVersion.kotlin
}

val kotestSpringRestdocsVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.github.xeounxzxu:kotest-spring-restdocs:$kotestSpringRestdocsVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${PluginsVersion.restdocs}")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:${PluginsVersion.kotest}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${PluginsVersion.kotest}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${PluginsVersion.extensionsSpring}")
}

configurations.all {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
}

tasks.test {
    useJUnitPlatform()
}

// Skip building this sample when running publish-oriented tasks for the library module
val skipForLibraryRelease = gradle.startParameter.taskNames.any { taskName ->
    taskName == "publishToMavenLocal" || taskName.endsWith(":publishToMavenLocal")
} && gradle.startParameter.taskNames.none { it.startsWith(project.path) }

tasks.configureEach {
    onlyIf { !skipForLibraryRelease || name == "clean" }
}
