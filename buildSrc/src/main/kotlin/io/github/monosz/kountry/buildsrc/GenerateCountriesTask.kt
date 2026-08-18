package io.github.monosz.kountry.buildsrc

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class GenerateCountriesTask : DefaultTask() {
    @get:InputFile
    abstract val csvFile: RegularFileProperty

    @get:OutputFile
    abstract val countryKtFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val beginMarker = "// ### BEGIN AUTO-GENERATED ###"
        val endMarker = "// ### END AUTO-GENERATED ###"
        val indent = "        "
        val csv = csvFile.get().asFile
        val ktFile = countryKtFile.get().asFile
        require(csv.exists()) { "Missing CSV: $csv" }
        require(ktFile.exists()) { "Missing Country.kt: $ktFile" }

        val lines = csv.readText().lines().filter { it.isNotBlank() }
        require(lines.isNotEmpty()) { "CSV is empty: $csv" }

        val required = listOf("iso2", "iso3", "countryCode", "callingCode", "currencyCode")
        val header = lines.first().split(',')
        require(header == required) {
            "CSV header must be ${required.joinToString(",")}; was ${header.joinToString(",")}"
        }

        val iso2List = mutableListOf<String>()
        val iso3List = mutableListOf<String>()
        val countryCodeList = mutableListOf<String>()
        val callingCodeList = mutableListOf<String>()
        val currencyCodeList = mutableListOf<String?>()

        lines.drop(1).forEachIndexed { index, line ->
            val cols = line.split(',')
            require(cols.size == required.size) {
                "CSV line ${index + 2} must have ${required.size} columns: $line"
            }
            val iso2 = cols[0].trim()
            val iso3 = cols[1].trim()
            val countryCode = cols[2].trim()
            val callingCode = cols[3].trim()
            val currencyRaw = cols[4].trim()
            require(iso2.matches(Regex("[A-Z]{2}"))) { "Invalid iso2 on line ${index + 2}: $iso2" }
            require(iso3.matches(Regex("[A-Z]{3}"))) { "Invalid iso3 on line ${index + 2}: $iso3" }
            require(countryCode.matches(Regex("\\d{3}"))) {
                "Invalid countryCode on line ${index + 2}: $countryCode"
            }
            require(callingCode.matches(Regex("\\+\\d+"))) {
                "Invalid callingCode on line ${index + 2}: $callingCode"
            }
            require(currencyRaw.isEmpty() || currencyRaw.matches(Regex("[A-Z]{3}"))) {
                "Invalid currencyCode on line ${index + 2}: $currencyRaw"
            }
            iso2List += iso2
            iso3List += iso3
            countryCodeList += countryCode
            callingCodeList += callingCode
            currencyCodeList += currencyRaw.ifEmpty { null }
        }

        assertUnique("iso2", iso2List)
        assertUnique("iso3", iso3List)
        assertUnique("countryCode", countryCodeList)

        val constants = iso3List.indices.joinToString("\n") { i ->
            val currency = currencyCodeList[i]?.let { "\"$it\"" } ?: "null"
            "${indent}val ${iso3List[i]} = Country(\"${iso2List[i]}\", \"${iso3List[i]}\", \"${countryCodeList[i]}\", \"${callingCodeList[i]}\", $currency)"
        }
        val allLines = iso3List.chunked(16).joinToString("\n") { chunk ->
            "$indent    ${chunk.joinToString(", ")},"
        }
        val generated = buildString {
            appendLine(constants)
            appendLine()
            appendLine("${indent}/** List of all available countries */")
            appendLine("${indent}val all: List<Country> = listOf(")
            appendLine(allLines)
            append("${indent})")
        }

        val original = ktFile.readText()
        val beginCount = Regex(Regex.escape(beginMarker)).findAll(original).count()
        val endCount = Regex(Regex.escape(endMarker)).findAll(original).count()
        require(beginCount == 1 && endCount == 1) {
            "Expected exactly one BEGIN and one END marker in Country.kt (begin=$beginCount, end=$endCount)"
        }

        val beginIndex = original.indexOf(beginMarker)
        val endIndex = original.indexOf(endMarker)
        require(beginIndex in 0..<endIndex) { "Invalid marker positions in Country.kt" }

        val before = original.substring(0, beginIndex + beginMarker.length)
        val after = original.substring(endIndex)
        val updated = before + "\n" + generated + "\n" + indent + after.trimStart()

        if (updated != original) {
            ktFile.writeText(updated)
            logger.lifecycle("Updated ${ktFile.name} with ${iso3List.size} countries")
        } else {
            logger.lifecycle("${ktFile.name} already up to date (${iso3List.size} countries)")
        }
    }

    private fun assertUnique(label: String, values: List<String>) {
        val dupes = values.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        require(dupes.isEmpty()) { "Duplicate $label values: $dupes" }
    }
}
