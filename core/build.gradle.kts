@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
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
        namespace = "io.github.monosz.kountry.core"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Core"
            isStatic = true
        }
    }

    js(IR) { browser() }
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    pom {
        name = "Kountry Core"
        description = "Lightweight Kotlin Multiplatform country metadata library"
        inceptionYear = "2026"
        url = "https://github.com/monosz/kountry"
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
