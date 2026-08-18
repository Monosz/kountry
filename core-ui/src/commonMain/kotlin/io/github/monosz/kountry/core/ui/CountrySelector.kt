package io.github.monosz.kountry.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.monosz.kountry.core.Country
import kotlinx.coroutines.launch

/**
 * Country selector with a clickable field that opens a bottom sheet.
 *
 * @param selectedCountry Currently selected country, or `null` if none
 * @param onCountrySelect Callback invoked when a country is selected in the picker
 * @param modifier Modifier applied to the field
 * @param enabled Whether the field is enabled
 * @param locale BCP 47 language tag for localized picker text, or `null` for system default
 * @param selectorPicker Picker content shown inside the bottom sheet
 * @param selectorField Clickable field content to toggle the picker
 *
 * @see CountryPicker
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelector(
    selectedCountry: Country?,
    onCountrySelect: (Country) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    locale: String? = null,
    selectorPicker: @Composable (
        selectedCountry: Country?,
        onCountrySelect: (Country) -> Unit,
    ) -> Unit = { selectedCountry, onCountrySelect ->
        CountryPicker(
            selectedCountry = selectedCountry,
            onClick = onCountrySelect,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            locale = locale,
        )
    },
    selectorField: @Composable (
        country: Country?,
        onClick: () -> Unit,
    ) -> Unit = { country, onClick ->
        CountrySelectorDefaults.SelectorField(
            country = country,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            locale = locale,
        )
    },
) {
    var showSheet by rememberSaveable { mutableStateOf(false) }

    selectorField(selectedCountry) { showSheet = true }

    if (showSheet) {
        SelectorBottomSheet(
            onDismissRequest = { showSheet = false },
        ) { onClose ->
            selectorPicker(selectedCountry) { country ->
                onCountrySelect(country)
                onClose()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable (onClose: () -> Unit) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val onClose: () -> Unit = {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            onDismissRequest()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onClose,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        content(onClose)
    }
}

/**
 * Default building blocks for [CountrySelector]
 */
object CountrySelectorDefaults {
    /**
     * Default clickable field showing flag, localized name, and a dropdown affordance.
     *
     * @param country Selected country, or `null` if none
     * @param onClick Callback invoked when the field is clicked
     * @param modifier Modifier applied to the text field
     * @param enabled Whether the field is enabled
     * @param locale BCP 47 language tag, or `null` for system default
     * @param label Optional text field label
     * @param placeholder Optional text field placeholder
     * @param interactionSource Optional interaction source to observe interaction
     */
    @Composable
    fun SelectorField(
        country: Country?,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        locale: String? = null,
        label: String? = null,
        placeholder: String? = null,
        interactionSource: MutableInteractionSource? = null,
    ) {
        val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

        Box(modifier = modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = country?.displayName(locale).orEmpty(),
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = true,
                label = label?.let { { Text(it) } },
                placeholder = placeholder?.let { { Text(it) } },
                leadingIcon = country?.let { { Text(it.flag) } },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                    )
                },
                interactionSource = interactionSource,
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled,
                        onClick = onClick,
                    ),
            )
        }
    }
}
