@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

@OptIn(ExperimentalWasmDsl::class)
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.dokka)
}

group = property("GROUP") as String
version = property("VERSION") as String

kotlin {
    jvm()
    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "io.github.monosz.kountry.core.ui"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "CoreUi"
            isStatic = true
        }
    }

    js(IR) { browser() }
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)

            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material.icons)
            implementation(libs.compose.resources)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.github.monosz.core.ui.resources"
    generateResClass = always
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    pom {
        name = "Kountry Compose"
        description = "Compose Multiplatform country picker UI components"
        inceptionYear = "2026"
        url = "https://github.com/monosz/kountry/"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }

        developers {
            developer {
                id = "monosz"
                name = "Monosz"
                url = "https://github.com/monosz"
            }
        }

        scm {
            url = "https://github.com/monosz/kountry"
            connection = "scm:git:git://github.com/monosz/kountry.git"
            developerConnection = "scm:git:ssh://git@github.com/monosz/kountry.git"
        }
    }
}
