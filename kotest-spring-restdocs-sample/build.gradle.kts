plugins {
    id("org.springframework.boot") version PluginsVersion.springBoot
    id("io.spring.dependency-management") version PluginsVersion.springDependencyManagement
    kotlin("plugin.spring") version PluginsVersion.kotlin
}

dependencies {
    // sample project
    // fixme : last version remove and local project add
    implementation(project(":kotest-spring-restdocs"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

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
