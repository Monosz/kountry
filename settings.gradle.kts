rootProject.name = "kountry"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://redirector.kotlinlang.org/maven/compose-dev")
    }
}

include(":core")
include(":core-ui")
include(":sample")
include(":androidApp")
include(":jvmApp")
include(":webApp")
