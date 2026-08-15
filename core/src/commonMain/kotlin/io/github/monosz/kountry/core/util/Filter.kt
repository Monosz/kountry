package io.github.monosz.kountry.core.util

import io.github.monosz.kountry.core.Country

/**
 * Filters countries by [query] using the selected [fields].
 *
 * Matching is case-insensitive substring matching, with fields combined using OR semantics.
 *
 * @param query Text to search for
 * @param locale BCP 47 language tag for localized fields, or `null` for the system default
 * @param fields Fields to search. Defaults to [FilterField.all]
 */
fun List<Country>.filterByQuery(
    query: String,
    locale: String? = null,
    fields: Set<FilterField> = FilterField.all,
): List<Country> {
    val q = query.trim()
    if (q.isBlank()) return this

    return this.filter { country ->
        fields.any { field ->
            when (field) {
                FilterField.Iso2 ->
                    country.iso2.contains(q, ignoreCase = true)

                FilterField.Iso3 ->
                    country.iso3.contains(q, ignoreCase = true)

                FilterField.CountryCode ->
                    country.countryCode.contains(q, ignoreCase = true)

                FilterField.CallingCode ->
                    country.callingCode.contains(q, ignoreCase = true)

                FilterField.CurrencyCode ->
                    country.currencyCode?.contains(q, ignoreCase = true) == true

                FilterField.DisplayName ->
                    country.displayName(locale).contains(q, ignoreCase = true)

                FilterField.CurrencySymbol ->
                    country.currencySymbol(locale)?.contains(q, ignoreCase = true) == true

                FilterField.CurrencyName ->
                    country.currencyName(locale)?.contains(q, ignoreCase = true) == true
            }
        }
    }
}

/**
 * Country attributes that [filterByQuery] can match against.
 */
enum class FilterField {
    Iso2,
    Iso3,
    CountryCode,
    CallingCode,
    CurrencyCode,
    DisplayName,
    CurrencySymbol,
    CurrencyName;

    companion object {
        val country = setOf(Iso2, Iso3, CountryCode, DisplayName)
        val calling = setOf(DisplayName, CallingCode)
        val currency = setOf(CurrencySymbol, CurrencyCode, CurrencyName)
        val all = entries.toSet()
    }
}
