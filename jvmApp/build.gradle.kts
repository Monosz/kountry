plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(projects.sample)
    implementation(projects.coreUi)
    implementation(compose.desktop.currentOs)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
}

compose.desktop {
    application {
        mainClass = "io.github.monosz.kountry.MainKt"
    }
}
