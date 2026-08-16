package io.github.monosz.kountry.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KountryTest {
    private val countries = Kountry.all

    @Test
    fun `all iso2 codes should be unique`() {
        val iso2Codes = countries.map { it.iso2 }
        assertEquals(iso2Codes.size, iso2Codes.toSet().size, "Duplicate iso2 codes found")
    }

    @Test
    fun `all iso3 codes should be unique`() {
        val iso3Codes = countries.map { it.iso3 }
        assertEquals(iso3Codes.size, iso3Codes.toSet().size, "Duplicate iso3 codes found")
    }

    @Test
    fun `all countryCode codes should be unique three-digit values`() {
        val countryCodes = countries.map { it.countryCode }
        assertEquals(countryCodes.size, countryCodes.toSet().size, "Duplicate countryCode codes found")
        countries.forEach { country ->
            assertTrue(
                country.countryCode.matches(Regex("\\d{3}")),
                "countryCode must be 3 digits for ${country.iso2}, was ${country.countryCode}",
            )
        }
    }

    // TODO: Add more test?
}
