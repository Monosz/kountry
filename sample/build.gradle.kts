@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
}

kotlin {
    jvm()
    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "io.github.monosz.kountry.sample"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "Sample"
            isStatic = true
        }
    }

    js(IR) { browser() }
    wasmJs { browser() }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
        }

        commonMain.dependencies {
            implementation(projects.core)
            // implementation(projects.coreUi)

            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}

dependencies {
    dokka(projects.core)

    // Hide core-ui for now
    if (file("core-ui").exists()) {
        dokka(projects.coreUi)
    }
}

dokka {
    moduleName.set("Kountry")

    dokkaSourceSets.configureEach {
        sourceLink {
            localDirectory.set(file("src"))
            remoteUrl("https://github.com/monosz/kountry")
            remoteLineSuffix.set("#L")
        }
    }
}
