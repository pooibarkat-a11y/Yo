package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.example.model.AppThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldLight,
    onPrimary = Color(0xFF042116),
    primaryContainer = EmeraldDark,
    onPrimaryContainer = Color(0xFFA7F3D0),
    secondary = GoldLight,
    onSecondary = Color(0xFF261900),
    secondaryContainer = Color(0xFF42300B),
    onSecondaryContainer = Color(0xFFFFE599),
    tertiary = CelestialBlue,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldContainer,
    onPrimaryContainer = EmeraldDark,
    secondary = GoldAccent,
    onSecondary = Color(0xFF221600),
    secondaryContainer = Color(0xFFFFF6DF),
    onSecondaryContainer = Color(0xFF5D430A),
    tertiary = CelestialBlue,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private val SepiaColorScheme = lightColorScheme(
    primary = SepiaPrimary,
    onPrimary = Color.White,
    primaryContainer = SepiaSurfaceVariant,
    onPrimaryContainer = SepiaOnSurface,
    secondary = GoldAccent,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFF3E7D3),
    onSecondaryContainer = Color(0xFF4F341C),
    background = SepiaBackground,
    onBackground = SepiaOnSurface,
    surface = SepiaSurface,
    onSurface = SepiaOnSurface,
    surfaceVariant = SepiaSurfaceVariant,
    onSurfaceVariant = SepiaOnSurfaceVariant
)

@Composable
fun TarteelTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val colorScheme: ColorScheme = when (themeMode) {
        AppThemeMode.DARK -> DarkColorScheme
        AppThemeMode.LIGHT -> LightColorScheme
        AppThemeMode.SEPIA -> SepiaColorScheme
    }

    val typography = remember(isTablet) {
        getAdaptiveTypography(isTablet)
    }

    CompositionLocalProvider(LocalIsTablet provides isTablet) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
