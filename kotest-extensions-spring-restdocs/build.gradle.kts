//val pluginsVersion = PluginsVersion
//val kotestExtensionsSpringRestdocs = pluginsVersion.kotestExtensionsSpringRestdocs
//
//plugins {
//    `maven-publish`
//    `java-library`
//}
//
//version = kotestExtensionsSpringRestdocs
//
//dependencies {
//
//
//    implementation("org.springframework.restdocs:spring-restdocs-mockmvc:${pluginsVersion.restdocs}")
//    implementation("org.springframework.restdocs:spring-restdocs-restassured:${pluginsVersion.restdocs}")
//    implementation("io.kotest:kotest-runner-junit5-jvm:${pluginsVersion.kotest}")
//    implementation("io.kotest:kotest-assertions-core-jvm:${pluginsVersion.kotest}")
//    implementation("io.kotest:kotest-property:${pluginsVersion.kotest}")
//    implementation("io.kotest.extensions:kotest-extensions-spring:${pluginsVersion.extensionsSpring}")
//}
//
//tasks.getByName("jar") {
//    enabled = true
//}
//
////publishing {
////    publications {
////        create<MavenPublication>("mavenJava") {
////            groupId = "com.github.xeounxzxu"
////            artifactId = "kotest-extensions-spring-restdocs"
////            version = kotestExtensionsSpringRestdocs
////            from(components["java"])
////            versionMapping {
////                usage("java-api") {
////                    fromResolutionOf("runtimeClasspath")
////                }
////                usage("java-runtime") {
////                    fromResolutionResult()
////                }
////            }
////            pom {
////                name = "kotest spring restdocs extensions"
////                description = "kotest spring restdocs extensions library"
////            }
////        }
////    }
////}
//
//publishing {
//    publications {
//        create("maven-public", MavenPublication::class) {
//            groupId = "com.github.xeounxzxu"
//            artifactId = "kotest-extensions-spring-restdocs"
//            version = "1.0.0"
//            from(components.getByName("java"))
//        }
//    }
//}



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
            artifactId = "kotest-extensions-spring-restdocs"
            version = "1.0.0"
            from(components["java"])
        }
    }
}
