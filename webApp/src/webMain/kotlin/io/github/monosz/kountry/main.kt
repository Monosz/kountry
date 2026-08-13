package io.github.monosz.kountry

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.monosz.kountry.sample.SampleApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        SampleApp()
    }
}
