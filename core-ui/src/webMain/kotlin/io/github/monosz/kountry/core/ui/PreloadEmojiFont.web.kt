package io.github.monosz.kountry.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import io.github.monosz.core.ui.resources.NotoColorEmoji
import io.github.monosz.core.ui.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

/**
 * Preloads the Noto Color Emoji font before displaying [content].
 *
 * This is needed on web targets where system emoji fonts may not be resolved
 * automatically, causing color emoji to render as tofu (□).
 *
 * @param loading Composable shown while the font loads
 * @param content Composable shown after the font is loaded
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun PreloadEmojiFont(
    loading: @Composable () -> Unit = { DefaultLoading() },
    content: @Composable () -> Unit,
) {
    val resolver = LocalFontFamilyResolver.current
    val emojiFont by preloadFont(Res.font.NotoColorEmoji)
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(resolver, emojiFont) {
        if (emojiFont != null) {
            resolver.preload(FontFamily(listOfNotNull(emojiFont)))
            ready = true
        }
    }

    Crossfade(ready) { rd ->
        if (rd) {
            content()
        } else {
            loading()
        }
    }
}

@Composable
private fun DefaultLoading() {
    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
