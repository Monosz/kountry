package io.github.monosz.kountry.core

object Kountry {
    /**
     * List of all available countries.
     */
    val all: List<Country> = Country.all

    /**
     * Returns the country matching the given ISO 3166-1 alpha-2 code.
     */
    fun byIso2(code: String): Country? {
        val normalized = code.trim().uppercase()
        return byIso2Map[normalized]
    }

    /**
     * Returns the country matching the given ISO 3166-1 alpha-3 code.
     */
    fun byIso3(code: String): Country? {
        val normalized = code.trim().uppercase()
        return byIso3Map[normalized]
    }

    /**
     * Returns the country matching the given ISO 3166-1 numeric code.
     */
    fun byCountryCode(code: String): Country? {
        val normalized = code.trim().padStart(3, '0')
        return byCountryCodeMap[normalized]
    }

    /**
     * Returns all countries that use the given ITU-T E.164 country calling code.
     */
    fun byCallingCode(callingCode: String): List<Country> {
        val normalized = callingCode.trim().removePrefix("+")
        return byCallingCodeMap["+$normalized"] ?: emptyList()
    }

    /**
     * Returns all countries that use the given ISO 4217 currency code.
     */
    fun byCurrencyCode(code: String): List<Country> {
        val normalized = code.trim().uppercase()
        return byCurrencyCodeMap[normalized] ?: emptyList()
    }

    private val byIso2Map by lazy { all.associateBy { it.iso2 } }
    private val byIso3Map by lazy { all.associateBy { it.iso3 } }
    private val byCountryCodeMap by lazy { all.associateBy { it.countryCode } }
    private val byCallingCodeMap by lazy { all.groupBy { it.callingCode } }
    private val byCurrencyCodeMap by lazy {
        all.mapNotNull { country ->
            country.currencyCode?.let { it to country }
        }.groupBy({ it.first }, { it.second })
    }
}
