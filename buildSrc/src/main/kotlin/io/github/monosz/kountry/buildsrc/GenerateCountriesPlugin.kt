package io.github.monosz.kountry.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class GenerateCountriesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register<GenerateCountriesTask>("generateCountries") {
            group = "build"
            description = "Generate Country companion constants from data/country_list.csv"
            csvFile.set(target.rootProject.layout.projectDirectory.file("data/country_list.csv"))
            countryKtFile.set(
                target.layout.projectDirectory.file(
                    "src/commonMain/kotlin/io/github/monosz/kountry/core/Country.kt",
                ),
            )
        }
    }
}
