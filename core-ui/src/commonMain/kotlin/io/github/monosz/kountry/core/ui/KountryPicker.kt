package io.github.monosz.kountry.core.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemElevation
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.Kountry
import io.github.monosz.kountry.core.util.FilterField
import io.github.monosz.kountry.core.util.filterByQuery

/**
 * Searchable country picker with selectable rows.
 *
 * This should be placed inside a container such as a dialog or bottom sheet.
 * It provides customizable search and item content for different use cases,
 * such as country, calling-code, or currency picking.
 *
 * @param onClick Callback invoked when a country row is clicked
 * @param selectedCountry Currently selected country, or `null` if none
 * @param modifier Modifier applied to the root column
 * @param countries List of country to display
 * @param locale BCP 47 language tag for localized content, or `null` for system default
 * @param searchField Search field content, or `null` to hide it
 * @param searchFilter Filters countries for the current query
 * @param emptyContent Empty state content when no countries match the current query
 * @param itemContent Content for each country row
 */
@Composable
fun KountryPicker(
    onClick: (Country) -> Unit,
    selectedCountry: Country?,
    modifier: Modifier = Modifier,
    countries: List<Country> = Kountry.all,
    locale: String? = null,
    searchField: @Composable ((
        query: String,
        onQueryChange: (String) -> Unit,
    ) -> Unit)? = { query, onQueryChange ->
        KountryPickerDefaults.SearchField(query, onQueryChange)
    },
    searchFilter: (query: String, countries: List<Country>) -> List<Country> = { query, countries ->
        countries.filterByQuery(query, locale, FilterField.country)
    },
    emptyContent: @Composable () -> Unit = {
        KountryPickerDefaults.EmptyContent()
    },
    itemContent: @Composable (
        country: Country,
        selected: Boolean,
        onClick: () -> Unit,
    ) -> Unit = { country, selected, onClick ->
        KountryPickerDefaults.CountryItem(
            country = country,
            selected = selected,
            onClick = onClick,
            locale = locale,
        )
    },
) {
    var query by rememberSaveable { mutableStateOf("") }
    val filteredCountries = searchFilter(query, countries)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        searchField?.invoke(query) { query = it }

        LazyColumn(
            modifier = Modifier.weight(1f, false),
        ) {
            if (filteredCountries.isEmpty()) {
                item {
                    emptyContent()
                }
            } else {
                items(
                    items = filteredCountries,
                    key = { it.iso2 },
                ) { country ->
                    itemContent(
                        country,
                        country == selectedCountry,
                        { onClick(country) },
                    )
                }
            }
        }
    }
}

/**
 * Default building blocks for [KountryPicker].
 *
 * Use these as-is via the picker defaults, or pass them explicitly into [KountryPicker]
 * slots when composing a custom layout.
 */
object KountryPickerDefaults {
    /**
     * Default search field for [KountryPicker].
     *
     * @param query Current search query
     * @param onQueryChange Callback invoked when the query changes
     * @param modifier Modifier applied to the field
     */
    @Composable
    fun SearchField(
        query: String,
        onQueryChange: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier.fillMaxWidth(0.95f),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = if (query.isNotBlank()) {
                {
                    IconButton(
                        onClick = { onQueryChange("") },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                    }
                }
            } else {
                null
            },
            shape = RoundedCornerShape(100),
        )
    }

    /**
     * Default empty state for [KountryPicker] when no countries match the current query.
     *
     * @param modifier Modifier applied to the root column
     * @param label Primary message
     * @param description Optional supporting message below [label]
     */
    @Composable
    fun EmptyContent(
        modifier: Modifier = Modifier,
        label: String = "No countries found",
        description: String? = null,
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
            )
            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }

    /**
     * Default country row showing the flag, localized name, and selection state.
     *
     * @param country Country to display
     * @param selected Whether the country is selected
     * @param onClick Callback invoked when the row is clicked
     * @param modifier Modifier applied to the row
     * @param locale BCP 47 language tag, or `null` for system default
     */
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun CountryItem(
        country: Country,
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        locale: String? = null,
    ) {
        BaseListItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            leadingContent = {
                Text(country.flag)
            },
            trailingContent = {
                RadioButton(
                    selected = selected,
                    onClick = null,
                )
            },
        ) {
            Text(country.displayName(locale))
        }
    }

    /**
     * Default calling-code row showing the flag, localized name, and calling code.
     *
     * @param country Country to display
     * @param selected Whether the country is selected
     * @param onClick Callback invoked when the row is clicked
     * @param modifier Modifier applied to the row
     * @param locale BCP 47 language tag, or `null` for system default
     */
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun CallingItem(
        country: Country,
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        locale: String? = null,
    ) {
        BaseListItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            leadingContent = {
                Text(country.flag)
            },
            trailingContent = {
                Text(country.callingCode)
            },
        ) {
            Text(country.displayName(locale))
        }
    }

    /**
     * Default currency row showing the flag, country name, currency name, and code.
     *
     * @param country Country to display
     * @param selected Whether the country is selected
     * @param onClick Callback invoked when the row is clicked
     * @param modifier Modifier applied to the row
     * @param locale BCP 47 language tag, or `null` for system default
     */
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun CurrencyItem(
        country: Country,
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        locale: String? = null,
    ) {
        BaseListItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            leadingContent = {
                Text(country.flag)
            },
            trailingContent = {
                Text(country.currencyCode.orEmpty())
            },
            overlineContent = {
                Text(country.displayName(locale))
            },
        ) {
            Text(country.currencyName(locale).orEmpty())
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    private fun BaseListItem(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        leadingContent: @Composable (() -> Unit)? = null,
        trailingContent: @Composable (() -> Unit)? = null,
        overlineContent: @Composable (() -> Unit)? = null,
        supportingContent: @Composable (() -> Unit)? = null,
        verticalAlignment: Alignment.Vertical = ListItemDefaults.verticalAlignment(),
        onLongClick: (() -> Unit)? = null,
        onLongClickLabel: String? = null,
        shapes: ListItemShapes = ListItemDefaults.shapes(),
        colors: ListItemColors = ListItemDefaults.colors(),
        elevation: ListItemElevation = ListItemDefaults.elevation(),
        contentPadding: PaddingValues = ListItemDefaults.ContentPadding,
        interactionSource: MutableInteractionSource? = null,
        content: @Composable () -> Unit,
    ) {
        ListItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
            overlineContent = overlineContent,
            supportingContent = supportingContent,
            verticalAlignment = verticalAlignment,
            onLongClick = onLongClick,
            onLongClickLabel = onLongClickLabel,
            interactionSource = interactionSource,
            colors = colors,
            shapes = shapes,
            elevation = elevation,
            contentPadding = contentPadding,
            content = content,
        )
    }
}
