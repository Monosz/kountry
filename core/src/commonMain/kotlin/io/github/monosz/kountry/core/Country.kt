package io.github.monosz.kountry.core

import androidx.compose.runtime.Immutable
import kotlin.text.forEach

/**
 * Represents a country using standardized ISO identifiers, telephony, and currency metadata
 *
 * @property iso2 ISO 3166-1 alpha-2 code (e.g., "US", "ID")
 * @property iso3 ISO 3166-1 alpha-3 code (e.g., "USA", "IDN")
 * @property countryCode ISO 3166-1 numeric code (e.g., "840", "360")
 * @property callingCode ITU-T E.164 country calling code with '+' prefix (e.g., "+1", "+62")
 * @property currencyCode ISO 4217 currency code, or null if none (e.g., "USD", null for Antarctica)
 *
 * @see <a href="https://www.iso.org/iso-3166-country-codes.html">ISO 3166 country codes</a>
 * @see <a href="https://www.iso.org/iso-4217-currency-codes.html">ISO 4217 currency codes</a>
 * @see <a href="https://www.itu.int/rec/T-REC-E.164">ITU-T E.164</a>
 */
@Immutable
data class Country(
    val iso2: String,
    val iso3: String,
    val countryCode: String,
    val callingCode: String,
    val currencyCode: String?,
) {
    /** Unicode flag emoji derived from [iso2] */
    val flag: String = buildString {
        iso2.forEach { char ->
            // Convert A-Z into the corresponding Regional Indicator code point.
            val codePoint = 0x1F1E6 + (char - 'A')

            // Encode the code point as a UTF-16 surrogate pair.
            append(((codePoint - 0x10000) shr 10 or 0xD800).toChar())
            append(((codePoint - 0x10000) and 0x3FF or 0xDC00).toChar())
        }
    }

    /**
     * Localized display name for this country.
     *
     * @param locale BCP 47 language tag (e.g. `"en-US"`), or `null` for the system default.
     */
    fun displayName(locale: String? = null): String =
        getPlatformDisplayName(iso2, locale)

    /**
     * Localized currency symbol for [currencyCode], or `null` if unavailable.
     *
     * @param locale BCP 47 language tag (e.g. `"en-US"`), or `null` for the system default.
     */
    fun currencySymbol(locale: String? = null): String? =
        currencyCode?.let { getPlatformCurrencySymbol(it, locale) }

    /**
     * Localized currency display name for [currencyCode], or `null` if unavailable.
     *
     * @param locale BCP 47 language tag (e.g. `"en-US"`), or `null` for the system default.
     */
    fun currencyName(locale: String? = null): String? =
        currencyCode?.let { getPlatformCurrencyName(it, locale) }

    /**
     * Predefined country constants named by ISO 3166-1 alpha-3 codes
     */
    companion object {
        // TODO: Add generator script
        val AFG = Country("AF", "AFG", "004", "+93", "AFN")
        val ALA = Country("AX", "ALA", "248", "+358", "EUR")
        val ALB = Country("AL", "ALB", "008", "+355", "ALL")
        val DZA = Country("DZ", "DZA", "012", "+213", "DZD")
        val ASM = Country("AS", "ASM", "016", "+1", "USD")
        val AND = Country("AD", "AND", "020", "+376", "EUR")
        val AGO = Country("AO", "AGO", "024", "+244", "AOA")
        val AIA = Country("AI", "AIA", "660", "+1", "XCD")
        val ATA = Country("AQ", "ATA", "010", "+672", null)
        val ATG = Country("AG", "ATG", "028", "+1", "XCD")
        val ARG = Country("AR", "ARG", "032", "+54", "ARS")
        val ARM = Country("AM", "ARM", "051", "+374", "AMD")
        val ABW = Country("AW", "ABW", "533", "+297", "AWG")
        val AUS = Country("AU", "AUS", "036", "+61", "AUD")
        val AUT = Country("AT", "AUT", "040", "+43", "EUR")
        val AZE = Country("AZ", "AZE", "031", "+994", "AZN")
        val BHS = Country("BS", "BHS", "044", "+1", "BSD")
        val BHR = Country("BH", "BHR", "048", "+973", "BHD")
        val BGD = Country("BD", "BGD", "050", "+880", "BDT")
        val BRB = Country("BB", "BRB", "052", "+1", "BBD")
        val BLR = Country("BY", "BLR", "112", "+375", "BYN")
        val BEL = Country("BE", "BEL", "056", "+32", "EUR")
        val BLZ = Country("BZ", "BLZ", "084", "+501", "BZD")
        val BEN = Country("BJ", "BEN", "204", "+229", "XOF")
        val BMU = Country("BM", "BMU", "060", "+1", "BMD")
        val BTN = Country("BT", "BTN", "064", "+975", "BTN")
        val BOL = Country("BO", "BOL", "068", "+591", "BOB")
        val BES = Country("BQ", "BES", "535", "+599", "USD")
        val BIH = Country("BA", "BIH", "070", "+387", "BAM")
        val BWA = Country("BW", "BWA", "072", "+267", "BWP")
        val BVT = Country("BV", "BVT", "074", "+47", "NOK")
        val BRA = Country("BR", "BRA", "076", "+55", "BRL")
        val IOT = Country("IO", "IOT", "086", "+246", "USD")
        val BRN = Country("BN", "BRN", "096", "+673", "BND")
        val BGR = Country("BG", "BGR", "100", "+359", "EUR")
        val BFA = Country("BF", "BFA", "854", "+226", "XOF")
        val BDI = Country("BI", "BDI", "108", "+257", "BIF")
        val KHM = Country("KH", "KHM", "116", "+855", "KHR")
        val CMR = Country("CM", "CMR", "120", "+237", "XAF")
        val CAN = Country("CA", "CAN", "124", "+1", "CAD")
        val CPV = Country("CV", "CPV", "132", "+238", "CVE")
        val CYM = Country("KY", "CYM", "136", "+1", "KYD")
        val CAF = Country("CF", "CAF", "140", "+236", "XAF")
        val TCD = Country("TD", "TCD", "148", "+235", "XAF")
        val CHL = Country("CL", "CHL", "152", "+56", "CLP")
        val CHN = Country("CN", "CHN", "156", "+86", "CNY")
        val CXR = Country("CX", "CXR", "162", "+61", "AUD")
        val CCK = Country("CC", "CCK", "166", "+61", "AUD")
        val COL = Country("CO", "COL", "170", "+57", "COP")
        val COM = Country("KM", "COM", "174", "+269", "KMF")
        val COG = Country("CG", "COG", "178", "+242", "XAF")
        val COD = Country("CD", "COD", "180", "+243", "CDF")
        val COK = Country("CK", "COK", "184", "+682", "NZD")
        val CRI = Country("CR", "CRI", "188", "+506", "CRC")
        val CIV = Country("CI", "CIV", "384", "+225", "XOF")
        val HRV = Country("HR", "HRV", "191", "+385", "EUR")
        val CUB = Country("CU", "CUB", "192", "+53", "CUP")
        val CUW = Country("CW", "CUW", "531", "+599", "XCG")
        val CYP = Country("CY", "CYP", "196", "+357", "EUR")
        val CZE = Country("CZ", "CZE", "203", "+420", "CZK")
        val DNK = Country("DK", "DNK", "208", "+45", "DKK")
        val DJI = Country("DJ", "DJI", "262", "+253", "DJF")
        val DMA = Country("DM", "DMA", "212", "+1", "XCD")
        val DOM = Country("DO", "DOM", "214", "+1", "DOP")
        val ECU = Country("EC", "ECU", "218", "+593", "USD")
        val EGY = Country("EG", "EGY", "818", "+20", "EGP")
        val SLV = Country("SV", "SLV", "222", "+503", "USD")
        val GNQ = Country("GQ", "GNQ", "226", "+240", "XAF")
        val ERI = Country("ER", "ERI", "232", "+291", "ERN")
        val EST = Country("EE", "EST", "233", "+372", "EUR")
        val ETH = Country("ET", "ETH", "231", "+251", "ETB")
        val FLK = Country("FK", "FLK", "238", "+500", "FKP")
        val FRO = Country("FO", "FRO", "234", "+298", "DKK")
        val FJI = Country("FJ", "FJI", "242", "+679", "FJD")
        val FIN = Country("FI", "FIN", "246", "+358", "EUR")
        val FRA = Country("FR", "FRA", "250", "+33", "EUR")
        val GUF = Country("GF", "GUF", "254", "+594", "EUR")
        val PYF = Country("PF", "PYF", "258", "+689", "XPF")
        val ATF = Country("TF", "ATF", "260", "+262", "EUR")
        val GAB = Country("GA", "GAB", "266", "+241", "XAF")
        val GMB = Country("GM", "GMB", "270", "+220", "GMD")
        val GEO = Country("GE", "GEO", "268", "+995", "GEL")
        val DEU = Country("DE", "DEU", "276", "+49", "EUR")
        val GHA = Country("GH", "GHA", "288", "+233", "GHS")
        val GIB = Country("GI", "GIB", "292", "+350", "GIP")
        val GRC = Country("GR", "GRC", "300", "+30", "EUR")
        val GRL = Country("GL", "GRL", "304", "+299", "DKK")
        val GRD = Country("GD", "GRD", "308", "+1", "XCD")
        val GLP = Country("GP", "GLP", "312", "+590", "EUR")
        val GUM = Country("GU", "GUM", "316", "+1", "USD")
        val GTM = Country("GT", "GTM", "320", "+502", "GTQ")
        val GGY = Country("GG", "GGY", "831", "+44", "GBP")
        val GIN = Country("GN", "GIN", "324", "+224", "GNF")
        val GNB = Country("GW", "GNB", "624", "+245", "XOF")
        val GUY = Country("GY", "GUY", "328", "+592", "GYD")
        val HTI = Country("HT", "HTI", "332", "+509", "HTG")
        val HMD = Country("HM", "HMD", "334", "+672", "AUD")
        val VAT = Country("VA", "VAT", "336", "+379", "EUR")
        val HND = Country("HN", "HND", "340", "+504", "HNL")
        val HKG = Country("HK", "HKG", "344", "+852", "HKD")
        val HUN = Country("HU", "HUN", "348", "+36", "HUF")
        val ISL = Country("IS", "ISL", "352", "+354", "ISK")
        val IND = Country("IN", "IND", "356", "+91", "INR")
        val IDN = Country("ID", "IDN", "360", "+62", "IDR")
        val IRN = Country("IR", "IRN", "364", "+98", "IRR")
        val IRQ = Country("IQ", "IRQ", "368", "+964", "IQD")
        val IRL = Country("IE", "IRL", "372", "+353", "EUR")
        val IMN = Country("IM", "IMN", "833", "+44", "GBP")
        val ISR = Country("IL", "ISR", "376", "+972", "ILS")
        val ITA = Country("IT", "ITA", "380", "+39", "EUR")
        val JAM = Country("JM", "JAM", "388", "+1", "JMD")
        val JPN = Country("JP", "JPN", "392", "+81", "JPY")
        val JEY = Country("JE", "JEY", "832", "+44", "GBP")
        val JOR = Country("JO", "JOR", "400", "+962", "JOD")
        val KAZ = Country("KZ", "KAZ", "398", "+7", "KZT")
        val KEN = Country("KE", "KEN", "404", "+254", "KES")
        val KIR = Country("KI", "KIR", "296", "+686", "AUD")
        val PRK = Country("KP", "PRK", "408", "+850", "KPW")
        val KOR = Country("KR", "KOR", "410", "+82", "KRW")
        val KWT = Country("KW", "KWT", "414", "+965", "KWD")
        val KGZ = Country("KG", "KGZ", "417", "+996", "KGS")
        val LAO = Country("LA", "LAO", "418", "+856", "LAK")
        val LVA = Country("LV", "LVA", "428", "+371", "EUR")
        val LBN = Country("LB", "LBN", "422", "+961", "LBP")
        val LSO = Country("LS", "LSO", "426", "+266", "LSL")
        val LBR = Country("LR", "LBR", "430", "+231", "LRD")
        val LBY = Country("LY", "LBY", "434", "+218", "LYD")
        val LIE = Country("LI", "LIE", "438", "+423", "CHF")
        val LTU = Country("LT", "LTU", "440", "+370", "EUR")
        val LUX = Country("LU", "LUX", "442", "+352", "EUR")
        val MAC = Country("MO", "MAC", "446", "+853", "MOP")
        val MKD = Country("MK", "MKD", "807", "+389", "MKD")
        val MDG = Country("MG", "MDG", "450", "+261", "MGA")
        val MWI = Country("MW", "MWI", "454", "+265", "MWK")
        val MYS = Country("MY", "MYS", "458", "+60", "MYR")
        val MDV = Country("MV", "MDV", "462", "+960", "MVR")
        val MLI = Country("ML", "MLI", "466", "+223", "XOF")
        val MLT = Country("MT", "MLT", "470", "+356", "EUR")
        val MHL = Country("MH", "MHL", "584", "+692", "USD")
        val MTQ = Country("MQ", "MTQ", "474", "+596", "EUR")
        val MRT = Country("MR", "MRT", "478", "+222", "MRU")
        val MUS = Country("MU", "MUS", "480", "+230", "MUR")
        val MYT = Country("YT", "MYT", "175", "+262", "EUR")
        val MEX = Country("MX", "MEX", "484", "+52", "MXN")
        val FSM = Country("FM", "FSM", "583", "+691", "USD")
        val MDA = Country("MD", "MDA", "498", "+373", "MDL")
        val MCO = Country("MC", "MCO", "492", "+377", "EUR")
        val MNG = Country("MN", "MNG", "496", "+976", "MNT")
        val MNE = Country("ME", "MNE", "499", "+382", "EUR")
        val MSR = Country("MS", "MSR", "500", "+1", "XCD")
        val MAR = Country("MA", "MAR", "504", "+212", "MAD")
        val MOZ = Country("MZ", "MOZ", "508", "+258", "MZN")
        val MMR = Country("MM", "MMR", "104", "+95", "MMK")
        val NAM = Country("NA", "NAM", "516", "+264", "NAD")
        val NRU = Country("NR", "NRU", "520", "+674", "AUD")
        val NPL = Country("NP", "NPL", "524", "+977", "NPR")
        val NLD = Country("NL", "NLD", "528", "+31", "EUR")
        val NCL = Country("NC", "NCL", "540", "+687", "XPF")
        val NZL = Country("NZ", "NZL", "554", "+64", "NZD")
        val NIC = Country("NI", "NIC", "558", "+505", "NIO")
        val NER = Country("NE", "NER", "562", "+227", "XOF")
        val NGA = Country("NG", "NGA", "566", "+234", "NGN")
        val NIU = Country("NU", "NIU", "570", "+683", "NZD")
        val NFK = Country("NF", "NFK", "574", "+672", "AUD")
        val MNP = Country("MP", "MNP", "580", "+1", "USD")
        val NOR = Country("NO", "NOR", "578", "+47", "NOK")
        val OMN = Country("OM", "OMN", "512", "+968", "OMR")
        val PAK = Country("PK", "PAK", "586", "+92", "PKR")
        val PLW = Country("PW", "PLW", "585", "+680", "USD")
        val PSE = Country("PS", "PSE", "275", "+970", "ILS")
        val PAN = Country("PA", "PAN", "591", "+507", "PAB")
        val PNG = Country("PG", "PNG", "598", "+675", "PGK")
        val PRY = Country("PY", "PRY", "600", "+595", "PYG")
        val PER = Country("PE", "PER", "604", "+51", "PEN")
        val PHL = Country("PH", "PHL", "608", "+63", "PHP")
        val PCN = Country("PN", "PCN", "612", "+64", "NZD")
        val POL = Country("PL", "POL", "616", "+48", "PLN")
        val PRT = Country("PT", "PRT", "620", "+351", "EUR")
        val PRI = Country("PR", "PRI", "630", "+1", "USD")
        val QAT = Country("QA", "QAT", "634", "+974", "QAR")
        val REU = Country("RE", "REU", "638", "+262", "EUR")
        val ROU = Country("RO", "ROU", "642", "+40", "RON")
        val RUS = Country("RU", "RUS", "643", "+7", "RUB")
        val RWA = Country("RW", "RWA", "646", "+250", "RWF")
        val BLM = Country("BL", "BLM", "652", "+590", "EUR")
        val SHN = Country("SH", "SHN", "654", "+290", "SHP")
        val KNA = Country("KN", "KNA", "659", "+1", "XCD")
        val LCA = Country("LC", "LCA", "662", "+1", "XCD")
        val MAF = Country("MF", "MAF", "663", "+590", "EUR")
        val SPM = Country("PM", "SPM", "666", "+508", "EUR")
        val VCT = Country("VC", "VCT", "670", "+1", "XCD")
        val WSM = Country("WS", "WSM", "882", "+685", "WST")
        val SMR = Country("SM", "SMR", "674", "+378", "EUR")
        val STP = Country("ST", "STP", "678", "+239", "STN")
        val SAU = Country("SA", "SAU", "682", "+966", "SAR")
        val SEN = Country("SN", "SEN", "686", "+221", "XOF")
        val SRB = Country("RS", "SRB", "688", "+381", "RSD")
        val SYC = Country("SC", "SYC", "690", "+248", "SCR")
        val SLE = Country("SL", "SLE", "694", "+232", "SLE")
        val SGP = Country("SG", "SGP", "702", "+65", "SGD")
        val SXM = Country("SX", "SXM", "534", "+1", "XCG")
        val SVK = Country("SK", "SVK", "703", "+421", "EUR")
        val SVN = Country("SI", "SVN", "705", "+386", "EUR")
        val SLB = Country("SB", "SLB", "090", "+677", "SBD")
        val SOM = Country("SO", "SOM", "706", "+252", "SOS")
        val ZAF = Country("ZA", "ZAF", "710", "+27", "ZAR")
        val SGS = Country("GS", "SGS", "239", "+500", "GBP")
        val SSD = Country("SS", "SSD", "728", "+211", "SSP")
        val ESP = Country("ES", "ESP", "724", "+34", "EUR")
        val LKA = Country("LK", "LKA", "144", "+94", "LKR")
        val SDN = Country("SD", "SDN", "729", "+249", "SDG")
        val SUR = Country("SR", "SUR", "740", "+597", "SRD")
        val SJM = Country("SJ", "SJM", "744", "+47", "NOK")
        val SWZ = Country("SZ", "SWZ", "748", "+268", "SZL")
        val SWE = Country("SE", "SWE", "752", "+46", "SEK")
        val CHE = Country("CH", "CHE", "756", "+41", "CHF")
        val SYR = Country("SY", "SYR", "760", "+963", "SYP")
        val TWN = Country("TW", "TWN", "158", "+886", "TWD")
        val TJK = Country("TJ", "TJK", "762", "+992", "TJS")
        val TZA = Country("TZ", "TZA", "834", "+255", "TZS")
        val THA = Country("TH", "THA", "764", "+66", "THB")
        val TLS = Country("TL", "TLS", "626", "+670", "USD")
        val TGO = Country("TG", "TGO", "768", "+228", "XOF")
        val TKL = Country("TK", "TKL", "772", "+690", "NZD")
        val TON = Country("TO", "TON", "776", "+676", "TOP")
        val TTO = Country("TT", "TTO", "780", "+1", "TTD")
        val TUN = Country("TN", "TUN", "788", "+216", "TND")
        val TUR = Country("TR", "TUR", "792", "+90", "TRY")
        val TKM = Country("TM", "TKM", "795", "+993", "TMT")
        val TCA = Country("TC", "TCA", "796", "+1", "USD")
        val TUV = Country("TV", "TUV", "798", "+688", "AUD")
        val UGA = Country("UG", "UGA", "800", "+256", "UGX")
        val UKR = Country("UA", "UKR", "804", "+380", "UAH")
        val ARE = Country("AE", "ARE", "784", "+971", "AED")
        val GBR = Country("GB", "GBR", "826", "+44", "GBP")
        val USA = Country("US", "USA", "840", "+1", "USD")
        val UMI = Country("UM", "UMI", "581", "+1", "USD")
        val URY = Country("UY", "URY", "858", "+598", "UYU")
        val UZB = Country("UZ", "UZB", "860", "+998", "UZS")
        val VUT = Country("VU", "VUT", "548", "+678", "VUV")
        val VEN = Country("VE", "VEN", "862", "+58", "VES")
        val VNM = Country("VN", "VNM", "704", "+84", "VND")
        val VGB = Country("VG", "VGB", "092", "+1", "USD")
        val VIR = Country("VI", "VIR", "850", "+1", "USD")
        val WLF = Country("WF", "WLF", "876", "+681", "XPF")
        val ESH = Country("EH", "ESH", "732", "+212", "MAD")
        val YEM = Country("YE", "YEM", "887", "+967", "YER")
        val ZMB = Country("ZM", "ZMB", "894", "+260", "ZMW")
        val ZWE = Country("ZW", "ZWE", "716", "+263", "ZWG")

        internal val all: List<Country> = listOf(
            AFG, ALA, ALB, DZA, ASM, AND, AGO, AIA, ATA, ATG, ARG, ARM, ABW, AUS, AUT, AZE,
            BHS, BHR, BGD, BRB, BLR, BEL, BLZ, BEN, BMU, BTN, BOL, BES, BIH, BWA, BVT, BRA,
            IOT, BRN, BGR, BFA, BDI, KHM, CMR, CAN, CPV, CYM, CAF, TCD, CHL, CHN, CXR, CCK,
            COL, COM, COG, COD, COK, CRI, CIV, HRV, CUB, CUW, CYP, CZE, DNK, DJI, DMA, DOM,
            ECU, EGY, SLV, GNQ, ERI, EST, ETH, FLK, FRO, FJI, FIN, FRA, GUF, PYF, ATF, GAB,
            GMB, GEO, DEU, GHA, GIB, GRC, GRL, GRD, GLP, GUM, GTM, GGY, GIN, GNB, GUY, HTI,
            HMD, VAT, HND, HKG, HUN, ISL, IND, IDN, IRN, IRQ, IRL, IMN, ISR, ITA, JAM, JPN,
            JEY, JOR, KAZ, KEN, KIR, PRK, KOR, KWT, KGZ, LAO, LVA, LBN, LSO, LBR, LBY, LIE,
            LTU, LUX, MAC, MKD, MDG, MWI, MYS, MDV, MLI, MLT, MHL, MTQ, MRT, MUS, MYT, MEX,
            FSM, MDA, MCO, MNG, MNE, MSR, MAR, MOZ, MMR, NAM, NRU, NPL, NLD, NCL, NZL, NIC,
            NER, NGA, NIU, NFK, MNP, NOR, OMN, PAK, PLW, PSE, PAN, PNG, PRY, PER, PHL, PCN,
            POL, PRT, PRI, QAT, REU, ROU, RUS, RWA, BLM, SHN, KNA, LCA, MAF, SPM, VCT, WSM,
            SMR, STP, SAU, SEN, SRB, SYC, SLE, SGP, SXM, SVK, SVN, SLB, SOM, ZAF, SGS, SSD,
            ESP, LKA, SDN, SUR, SJM, SWZ, SWE, CHE, SYR, TWN, TJK, TZA, THA, TLS, TGO, TKL,
            TON, TTO, TUN, TUR, TKM, TCA, TUV, UGA, UKR, ARE, GBR, USA, UMI, URY, UZB, VUT,
            VEN, VNM, VGB, VIR, WLF, ESH, YEM, ZMB, ZWE,
        )
    }
}
