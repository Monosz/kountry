package io.github.monosz.kountry.core

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js

@OptIn(ExperimentalWasmJsInterop::class)
private fun getWebLocale(): String =
    js("navigator.language")

internal actual fun getPlatformDisplayName(iso2: String, locale: String?): String {
    return try {
        val resolvedLocale = locale ?: getWebLocale()
        getDisplayNameJs(resolvedLocale, iso2)
    } catch (_: Throwable) {
        iso2
    }
}

internal actual fun getPlatformCurrencySymbol(currencyCode: String, locale: String?): String? {
    return try {
        val resolvedLocale = locale ?: getWebLocale()
        getCurrencySymbolJs(resolvedLocale, currencyCode)
    } catch (_: Throwable) {
        null
    }
}

internal actual fun getPlatformCurrencyName(currencyCode: String, locale: String?): String? {
    return try {
        val resolvedLocale = locale ?: getWebLocale()
        getCurrencyNameJs(resolvedLocale, currencyCode)
    } catch (_: Throwable) {
        null
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun getDisplayNameJs(locale: String, iso2: String): String =
    js("new Intl.DisplayNames([locale], { type: 'region' }).of(iso2)")

@OptIn(ExperimentalWasmJsInterop::class)
private fun getCurrencySymbolJs(locale: String, currencyCode: String): String =
    js("new Intl.NumberFormat(locale, { style: 'currency', currency: currencyCode, currencyDisplay: 'narrowSymbol' }).formatToParts(1).find(function(p){return p.type==='currency'}).value")

@OptIn(ExperimentalWasmJsInterop::class)
private fun getCurrencyNameJs(locale: String, currencyCode: String): String =
    js("new Intl.DisplayNames([locale], { type: 'currency' }).of(currencyCode)")
