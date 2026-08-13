package io.github.monosz.kountry

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.monosz.kountry.sample.SampleApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kountry Sample",
    ) {
        SampleApp()
    }
}
