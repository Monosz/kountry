import org.gradle.api.publish.PublishingExtension
import org.gradle.api.credentials.PasswordCredentials

plugins {
    alias(libs.plugins.androidApp) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.dokka) apply false
}

subprojects {
    plugins.withId("com.vanniktech.maven.publish") {
        extensions.configure<PublishingExtension>("publishing") {
            repositories {
                maven {
                    name = "githubPackages"
                    url = uri("https://maven.pkg.github.com/monosz/kountry")
                    credentials(PasswordCredentials::class)
                }
            }
        }
    }
}
