package io.github.monosz.kountry.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.ui.KountrySelector

@Composable
fun SampleApp() {
    SampleTheme {
        SampleScreen(
            country = null,
            locale = null,
        )
    }
}

@Composable
private fun SampleScreen(
    country: Country?,
    locale: String?,
) {
    val focusManager = LocalFocusManager.current
    var selectedCountry by remember { mutableStateOf(country) }
    var selectedLocale by remember { mutableStateOf(locale) }

    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = null,
            indication = null,
            onClick = { focusManager.clearFocus() },
        ),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "Kountry sample",
                style = MaterialTheme.typography.headlineSmall,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Display locale (BCP 47)",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Affects country names in the selector and details below.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    sampleLocales.forEach { locale ->
                        FilterChip(
                            selected = selectedLocale == locale,
                            onClick = { selectedLocale = locale },
                            label = { Text(locale ?: "System") },
                        )
                    }
                }
            }

            KountrySelector(
                selectedCountry = selectedCountry,
                onCountrySelect = { selectedCountry = it },
                modifier = Modifier.fillMaxWidth(),
                locale = selectedLocale,
            )

            selectedCountry?.let { c ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Selected country data",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text("${c.flag} ${c.displayName(selectedLocale)}")
                    Text("ISO 2: ${c.iso2}")
                    Text("ISO 3: ${c.iso3}")
                    Text("Calling code: ${c.callingCode}")
                    Text("Currency: ${c.currencyCode ?: "—"}")
                    Text("Currency name: ${c.currencyName(selectedLocale)}")
                    Text("Currency symbol: ${c.currencySymbol(selectedLocale)}")
                }
            }
        }
    }
}

private val sampleLocales = listOf(
    null,
    "en-US",
    "id-ID",
    "de-DE",
    "fr-FR",
    "es-ES",
)

@Preview
@Composable
private fun SampleScreenPreview() {
    SampleTheme {
        SampleScreen(
            country = Country.USA,
            locale = "id-ID",
        )
    }
}
