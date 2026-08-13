package io.github.monosz.kountry.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.monosz.kountry.core.Country

@Composable
fun SampleApp() {
    SampleTheme {
        SampleScreen(Country.USA, "id-ID")
    }
}

@Composable
private fun SampleScreen(
    country: Country?,
    locale: String?,
) {
    var currentCountry by remember { mutableStateOf(country) }
    var currentLocale by remember { mutableStateOf(locale) }

    // TODO: Add some kind of sample
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
        ) {
            Text("Current country: ${currentCountry?.iso2}")
            Text("Current locale: $currentLocale")
            Text("Current country flag: ${currentCountry?.flag}")
            Text("Current country name: ${currentCountry?.displayName(currentLocale)}")
        }
    }
}

@Preview
@Composable
private fun SampleScreenPreview() {
    SampleScreen(
        country = Country.USA,
        locale = "id-ID",
    )
}
