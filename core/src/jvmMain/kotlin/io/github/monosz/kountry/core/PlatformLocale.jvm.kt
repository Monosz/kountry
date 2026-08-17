package io.github.monosz.kountry.core

import java.util.Currency
import java.util.Locale

private fun getJvmLocale(locale: String?): Locale {
    return locale?.let { Locale.forLanguageTag(it) } ?: Locale.getDefault()
}

internal actual fun getPlatformDisplayName(iso2: String, locale: String?): String {
    val resolvedLocale = getJvmLocale(locale)
    return Locale.Builder()
        .setRegion(iso2)
        .build()
        .getDisplayCountry(resolvedLocale)
}

internal actual fun getPlatformCurrencySymbol(currencyCode: String, locale: String?): String? =
    runCatching {
        val resolvedLocale = getJvmLocale(locale)
        Currency.getInstance(currencyCode).getSymbol(resolvedLocale)
    }.getOrNull()

internal actual fun getPlatformCurrencyName(currencyCode: String, locale: String?): String? =
    runCatching {
        val resolvedLocale = getJvmLocale(locale)
        Currency.getInstance(currencyCode).getDisplayName(resolvedLocale)
    }.getOrNull()
