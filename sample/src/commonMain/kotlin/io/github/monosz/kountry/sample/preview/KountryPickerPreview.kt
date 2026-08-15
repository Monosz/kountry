package io.github.monosz.kountry.sample.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.Kountry
import io.github.monosz.kountry.core.ui.KountryPicker
import io.github.monosz.kountry.core.ui.KountryPickerDefaults
import io.github.monosz.kountry.core.util.FilterField
import io.github.monosz.kountry.core.util.filterByQuery
import io.github.monosz.kountry.sample.SampleTheme

@Preview(showBackground = true)
@Composable
private fun KountryPickerPreview(
    @PreviewParameter(PickerPreviewParameterProvider::class)
    param: PickerParameter,
) {
    val locale = "id-ID"
    var selectedCountry by remember { mutableStateOf(param.selectedCountry) }

    SampleTheme {
        KountryPicker(
            onClick = { selectedCountry = it },
            selectedCountry = selectedCountry,
            modifier = Modifier.padding(16.dp),
            countries = param.countries,
            locale = locale,
            searchFilter = { query, counties ->
                counties.filterByQuery(
                    query = query,
                    locale = locale,
                    fields = filterFieldsFor(param.variant),
                )
            },
            itemContent = { country, selected, onClick ->
                PickerItem(
                    country = country,
                    selected = selected,
                    onClick = onClick,
                    variant = param.variant,
                    locale = locale,
                )
            }
        )
    }
}

private fun filterFieldsFor(variant: PickerVariant): Set<FilterField> =
    when (variant) {
        PickerVariant.Country -> FilterField.country
        PickerVariant.Calling -> FilterField.calling
        PickerVariant.Currency -> FilterField.currency
    }

@Composable
private fun PickerItem(
    country: Country,
    selected: Boolean,
    onClick: () -> Unit,
    variant: PickerVariant,
    locale: String? = null,
) {
    when (variant) {
        PickerVariant.Country -> KountryPickerDefaults.CountryItem(
            country = country,
            selected = selected,
            onClick = onClick,
            locale = locale,
        )

        PickerVariant.Calling -> KountryPickerDefaults.CallingItem(
            country = country,
            selected = selected,
            onClick = onClick,
            locale = locale,
        )

        PickerVariant.Currency -> KountryPickerDefaults.CurrencyItem(
            country = country,
            selected = selected,
            onClick = onClick,
            locale = locale,
        )
    }
}

private class PickerPreviewParameterProvider : PreviewParameterProvider<PickerParameter> {
    override val values: Sequence<PickerParameter>
        get() = sequence {
            PickerVariant.entries.forEach {
                yield(PickerParameter(it))
            }
        }
}

private data class PickerParameter(
    val variant: PickerVariant,
    val countries: List<Country> = Kountry.all,
    val selectedCountry: Country? = null,
)

private enum class PickerVariant {
    Country,
    Calling,
    Currency,
}
