import org.gradle.internal.impldep.org.apache.ivy.plugins.parser.m2.PomModuleDescriptorBuilder

val kotestExtensionsSpringRestdocs = PluginsVersion.kotestExtensionsSpringRestdocs

plugins {
    `maven-publish`
}

version = kotestExtensionsSpringRestdocs

//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        mavenCentral()
//        maven { url 'https://jitpack.io' }
//    }
//}

dependencies {

    val version = PluginsVersion

    implementation("org.springframework.restdocs:spring-restdocs-mockmvc:${version.restdocs}")
    implementation("org.springframework.restdocs:spring-restdocs-restassured:${version.restdocs}")
    implementation("io.kotest:kotest-runner-junit5-jvm:${version.kotest}")
    implementation("io.kotest:kotest-assertions-core-jvm:${version.kotest}")
    implementation("io.kotest:kotest-property:${version.kotest}")
    implementation("io.kotest.extensions:kotest-extensions-spring:${version.extensionsSpring}")
}

tasks.getByName("jar") {
    enabled = true
}

//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            groupId = "com.github.xeounxzxu"
//            artifactId = "kotest-extensions-spring-restdocs"
//            version = kotestExtensionsSpringRestdocs
//            from(components["java"])
//            versionMapping {
//                usage("java-api") {
//                    fromResolutionOf("runtimeClasspath")
//                }
//                usage("java-runtime") {
//                    fromResolutionResult()
//                }
//            }
//            pom {
//                name = "kotest spring restdocs extensions"
//                description = "kotest spring restdocs extensions library"
//            }
//        }
//    }
//}

//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        mavenCentral()
//        maven { url 'https://jitpack.io' }
//    }
//}

publishing {
    publications {
        create("maven-public", MavenPublication::class) {
            groupId = "com.github.xeounxzxu"
            artifactId = "kotest-extensions-spring-restdocs"
            version = "1.0.0"
            from(components.getByName("java"))
        }
    }
}

