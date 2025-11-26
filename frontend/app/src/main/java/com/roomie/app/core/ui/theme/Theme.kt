package com.roomie.app.core.ui.theme

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.roomie.app.R

private val DarkColorScheme = darkColorScheme(
    primary   = Color(0xFFFF5383),
    secondary = Color(0xFFDD8CA5),
    tertiary  = Color(0xFF009AFA),
    background = Color(0xFF373737),
    surface    = Color(0xFF474747),
    scrim      = Color(0xFFC6C6C6)
)

private val LightColorScheme = lightColorScheme(
    primary   = Color(0xFF009AFA),
    secondary = Color(0xFF61AAE1),
    tertiary  = Color(0xFFFF5383),
    background = Color(0xFFE3E6E8),
    surface    = Color(0xFFFFFFFF),
    scrim      = Color(0xFF6A6A6A),
)
val darkLogo = R.drawable.logo
val lightLogo = R.drawable.logo
val RoomieGradient = Brush.horizontalGradient(
    listOf(Color(0xFF0083FA), Color(0xFFFF5383))
)

@Immutable
data class RoomieAssets(@DrawableRes val logoRes: Int)

private val LocalRoomieAssets = staticCompositionLocalOf {
    RoomieAssets(logoRes = R.drawable.logo)
}

object RoomieTheme {
    val assets: RoomieAssets
        @Composable get() = LocalRoomieAssets.current
}

@Composable
fun Roomie_AndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
    val assets = if (darkTheme) {
        RoomieAssets(logoRes = R.drawable.logo)
    } else {
        RoomieAssets(logoRes = R.drawable.logo)
    }

    CompositionLocalProvider(LocalRoomieAssets provides assets) {
        MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
    }
}