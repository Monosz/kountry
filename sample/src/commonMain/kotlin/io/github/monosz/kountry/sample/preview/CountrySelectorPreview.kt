package io.github.monosz.kountry.sample.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.ui.CountrySelector
import io.github.monosz.kountry.sample.SampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun CountrySelectorPreview() {
    val locale = "id-ID"
    var selectedCountry by remember { mutableStateOf<Country?>(Country.USA) }

    SampleTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                CountrySelector(
                    selectedCountry = selectedCountry,
                    onCountrySelect = { selectedCountry = it },
                    locale = locale,
                )
            }
        }
    }
}
