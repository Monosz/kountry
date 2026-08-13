package io.github.monosz.kountry.core

import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.currentLocale
import platform.Foundation.localizedStringForCountryCode
import platform.Foundation.localizedStringForCurrencyCode

private fun getIosLocale(locale: String?): NSLocale {
    return locale?.let { NSLocale(it) } ?: NSLocale.currentLocale
}

internal actual fun getPlatformDisplayName(iso2: String, locale: String?): String {
    val resolvedLocale = getIosLocale(locale)
    return resolvedLocale.localizedStringForCountryCode(iso2) ?: iso2
}

internal actual fun getPlatformCurrencySymbol(currencyCode: String, locale: String?): String? {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    formatter.locale = getIosLocale(locale)
    formatter.currencyCode = currencyCode
    return formatter.currencySymbol
}

internal actual fun getPlatformCurrencyName(currencyCode: String, locale: String?): String? {
    val resolvedLocale = getIosLocale(locale)
    return resolvedLocale.localizedStringForCurrencyCode(currencyCode)
}


