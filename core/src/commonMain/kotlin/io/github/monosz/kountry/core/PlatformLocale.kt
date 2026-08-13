package io.github.monosz.kountry.core

internal expect fun getPlatformDisplayName(
    iso2: String,
    locale: String?,
): String

internal expect fun getPlatformCurrencySymbol(
    currencyCode: String,
    locale: String?,
): String?

internal expect fun getPlatformCurrencyName(
    currencyCode: String,
    locale: String?,
): String?
