plugins {
    `maven-publish`
    `java-library`
}

group = "com.github.xeounxzxu"
version = "1.0.0"

val pluginVersion = PluginsVersion

dependencies {
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc:${pluginVersion.restdocs}")
    implementation("org.springframework.restdocs:spring-restdocs-restassured:${pluginVersion.restdocs}")
    implementation("io.kotest:kotest-runner-junit5-jvm:${pluginVersion.kotest}")
    implementation("io.kotest:kotest-assertions-core-jvm:${pluginVersion.kotest}")
    implementation("io.kotest:kotest-property:${pluginVersion.kotest}")
    implementation("io.kotest.extensions:kotest-extensions-spring:${pluginVersion.extensionsSpring}")
}

publishing {
    publications {
        create("maven-public", MavenPublication::class) {
            groupId = "com.github.xeounxzxu"
            artifactId = "kotest-spring-restdocs"
            version = "1.0.0"
            from(components["java"])
        }
    }
}
