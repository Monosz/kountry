# Kountry

[![Maven Central - core](https://img.shields.io/maven-central/v/io.github.monosz/kountry-core?label=kountry-core)](https://central.sonatype.com/artifact/io.github.monosz/kountry-core)
[![Maven Central - core-ui](https://img.shields.io/maven-central/v/io.github.monosz/kountry-core-ui?label=kountry-core-ui)](https://central.sonatype.com/artifact/io.github.monosz/kountry-core-ui)

Lightweight Kotlin Multiplatform country metadata library, sourced from ISO 3166-1, ISO 4217 and E.164, with optional Compose Multiplatform components.

Kountry also provides localized country and currency names and symbols using each platform's native locale APIs. It's meant for quick MVPs and prototypes, with emoji flags instead of image assets, but feel free to use it beyond that if you want to :)

## Installation

```kt
dependencies {
    val version = "0.0.1-rc.1"
    implementation("io.github.monosz:kountry-core:$version")
    // Optional CMP components
    implementation("io.github.monosz:kountry-core-ui:$version")
}
```

## Usage

### core

```kt
import io.github.monosz.kountry.core.Country
import io.github.monosz.kountry.core.Kountry

val all = Kountry.all
val usa = Country.USA
// Or find country by lookups like so:
// val usa = Kountry.byIso2("US")

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
import io.github.monosz.kountry.core.ui.KountrySelector

@Composable
fun MyScreen() {
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    KountrySelector(
        selectedCountry = selectedCountry,
        onCountrySelect = { selectedCountry = it },
    )
}
```

### Web

On JS/Wasm targets, wrap your content with `PreloadResource` so emoji flags can render properly:
```kt
import androidx.compose.ui.window.ComposeViewport
import io.github.monosz.kountry.core.ui.PreloadResource

fun main() {
    ComposeViewport {
        PreloadResource {
            // your content
        }
    }
}
```

## License

[MIT](LICENSE)
