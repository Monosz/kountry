# Kountry

GitHub Packages:

[![Release](https://img.shields.io/github/v/tag/monosz/kountry?sort=semver&filter=!*-rc.*&label=release)](https://github.com/Monosz/kountry/releases)
[![Prerelease](https://img.shields.io/github/v/tag/monosz/kountry?sort=semver&filter=*-rc.*&label=prerelease)](https://github.com/Monosz/kountry/packages)

Maven Central:

[![core](https://img.shields.io/maven-central/v/io.github.monosz/kountry-core?label=core)](https://central.sonatype.com/artifact/io.github.monosz/kountry-core)
[![core-ui](https://img.shields.io/maven-central/v/io.github.monosz/kountry-core-ui?label=core-ui)](https://central.sonatype.com/artifact/io.github.monosz/kountry-core-ui)

> [!IMPORTANT]
> The Maven Central artifacts are not ready for use. Breaking changes are expected before `v0.0.1`. I can't publish another Maven Central version till next month because I'm out of publishing quota ;-; 
> 
> Use the latest prelease if you want to try the project.

---

Lightweight Kotlin Multiplatform library for country metadata, with optional Compose Multiplatform UI components.

Kountry also provides localized country and currency names and symbols using each platform's native locale APIs. It's meant for quick MVPs and prototypes, with emoji flags instead of image assets, but feel free to use it beyond that if you want to :)

Supported Targets:

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?logo=apple&logoColor=white)
![JVM](https://img.shields.io/badge/JVM-ED8B00?logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=white)
![WebAssembly](https://img.shields.io/badge/Wasm-654FF0?logo=webassembly&logoColor=white)

## Installation

Add Maven Central if you haven't already:
```kt
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        mavenCentral()

        // Optional: required for prerelease builds
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/monosz/kountry")
        }
    }
}
```

Then add the dependency:
```kotlin
// module build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            val version = "0.0.1-rc.3" // Use the latest release or prerelease tag
            
            // Core country metadata
            implementation("io.github.monosz:kountry-core:$version")
            
            // Optional: Compose Multiplatform components
            implementation("io.github.monosz:kountry-core-ui:$version")
        }
    }
}
```

## Usage

### core

```kt
import io.github.monosz.kountry.core.Country

val all = Country.all
val usa = Country.USA
// Or find a country by lookups like so:
// val usa = Country.byIso2("US")

usa.flag                  // "🇺🇸"
usa.displayName()         // "United States"
usa.displayName("id-ID")  // "Amerika Serikat"
usa.callingCode           // "+1"
usa.currencyCode          // "USD"
usa.currencyName()        // "US Dollar"
usa.currencySymbol()      // "US$"
```

### core-ui

```kt
import androidx.compose.runtime.*
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.ui.CountrySelector

@Composable
fun MyScreen() {
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    CountrySelector(
        selectedCountry = selectedCountry,
        onCountrySelect = { selectedCountry = it },
    )
}
```

### Web

On JS/Wasm targets, wrap your content with `PreloadEmojiFont` so emoji flags can render properly:

```kt
import androidx.compose.ui.window.ComposeViewport
import io.github.monosz.kountry.core.ui.PreloadEmojiFont

fun main() {
    ComposeViewport {
        PreloadEmojiFont {
            // your content
        }
    }
}
```

For localized names in Chinese, Japanese, Arabic, or other non-Latin scripts, you may also need to preload a font that supports those characters.

## Data Sources

The [country dataset](data/country_list.csv) is based on the following sources, as of 2026/08/13:

- [ISO 3166-1](https://www.iso.org/iso-3166-country-codes.html) for country codes
- [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html) for currency codes
- [ITU-T E.164](https://www.itu.int/rec/T-REC-E.164) for calling codes

## License

[MIT](LICENSE)
