//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//}

rootProject.name = "kotest-extensions-spring"

include("kotest-extensions-spring-restdocs")


//pluginManagement {
//   val kotlinVersion = PluginsVersion.kotlin
//
////    "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
////    "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
//
//    resolutionStrategy {
//        eachPlugin {
//            when(requested.id.id) {
//                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
//                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
//            }
//        }
//    }
//}
