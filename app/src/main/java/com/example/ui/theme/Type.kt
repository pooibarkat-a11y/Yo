package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * CompositionLocal indicating whether the device is a Tablet/Foldable (screenWidthDp >= 600)
 */
val LocalIsTablet = compositionLocalOf { false }

/**
 * Local Arabic Fonts bundled directly into Android resources for optimal rendering & offline speed
 */
val CairoFontFamily = FontFamily(
    Font(R.font.cairo, FontWeight.Normal),
    Font(R.font.cairo, FontWeight.Medium),
    Font(R.font.cairo, FontWeight.SemiBold),
    Font(R.font.cairo, FontWeight.Bold)
)

val QuranFontFamily = FontFamily(
    Font(R.font.amiri, FontWeight.Normal),
    Font(R.font.amiri, FontWeight.Bold)
)

/**
 * Creates an adaptive Material3 Typography set tailored specifically for Arabic typography
 * on Mobile vs Tablet screens to guarantee crystal clear readability and balanced proportions.
 */
fun getAdaptiveTypography(isTablet: Boolean): Typography {
    val scale = if (isTablet) 1.20f else 1.0f
    val baseFont = CairoFontFamily

    return Typography(
        displayLarge = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Bold,
            fontSize = (32 * scale).sp,
            lineHeight = (44 * scale).sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Bold,
            fontSize = (28 * scale).sp,
            lineHeight = (38 * scale).sp
        ),
        displaySmall = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = (24 * scale).sp,
            lineHeight = (34 * scale).sp
        ),
        headlineLarge = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Bold,
            fontSize = (22 * scale).sp,
            lineHeight = (32 * scale).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = (20 * scale).sp,
            lineHeight = (30 * scale).sp
        ),
        headlineSmall = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = (18 * scale).sp,
            lineHeight = (28 * scale).sp
        ),
        titleLarge = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Bold,
            fontSize = (18 * scale).sp,
            lineHeight = (28 * scale).sp
        ),
        titleMedium = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = (16 * scale).sp,
            lineHeight = (26 * scale).sp
        ),
        titleSmall = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Medium,
            fontSize = (14 * scale).sp,
            lineHeight = (23 * scale).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Normal,
            fontSize = (15.5 * scale).sp,
            lineHeight = (26 * scale).sp,
            letterSpacing = 0.2.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * scale).sp,
            lineHeight = (24 * scale).sp
        ),
        bodySmall = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Normal,
            fontSize = (12 * scale).sp,
            lineHeight = (20 * scale).sp
        ),
        labelLarge = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = (13.5 * scale).sp,
            lineHeight = (21 * scale).sp
        ),
        labelMedium = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Medium,
            fontSize = (12 * scale).sp,
            lineHeight = (19 * scale).sp
        ),
        labelSmall = TextStyle(
            fontFamily = baseFont,
            fontWeight = FontWeight.Medium,
            fontSize = (10.5 * scale).sp,
            lineHeight = (17 * scale).sp
        )
    )
}

val MobileTypography = getAdaptiveTypography(isTablet = false)
val TabletTypography = getAdaptiveTypography(isTablet = true)

val Typography = MobileTypography
