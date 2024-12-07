package com.battman.core.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val primary: Color @Composable get
    val onPrimary: Color @Composable get
    val tertiary: Color @Composable get
    val onTertiary: Color @Composable get
    val background: Color @Composable get
    val onBackground: Color @Composable get
    val surface: Color @Composable get
    val onSurface: Color @Composable get
    val surfaceContainer: Color @Composable get
    val grayscale10: Color @Composable get
    val grayscale20: Color @Composable get
    val grayscale30: Color @Composable get
    val grayscale40: Color @Composable get
    val grayscale50: Color @Composable get
    val grayscale60: Color @Composable get
    val grayscale70: Color @Composable get
    val grayscale80: Color @Composable get
    val overlay: Color @Composable get
}

object ColorSchemes {
    object Light : ColorScheme {
        override val primary: Color @Composable get() = Color(0xFF415F91)
        override val onPrimary: Color @Composable get() = Color(0xFFFFFFFF)
        override val tertiary: Color @Composable get() = Color(0xFFfad8fd)
        override val onTertiary: Color @Composable get() = Color(0xFF28132e)
        override val background: Color @Composable get() = Color(0xFFFFFFFF)
        override val onBackground: Color @Composable get() = Color(0xFF191C20)
        override val surface: Color @Composable get() = Color(0xFFF9F9FF)
        override val onSurface: Color @Composable get() = Color(0xFF191C20)
        override val surfaceContainer: Color @Composable get() = Color(0xFFEDEDF4)
        override val grayscale10: Color @Composable get() = Color(0xFFCCCCCC)
        override val grayscale20: Color @Composable get() = Color(0xFFB3B3B3)
        override val grayscale30: Color @Composable get() = Color(0xFF999999)
        override val grayscale40: Color @Composable get() = Color(0xFF808080)
        override val grayscale50: Color @Composable get() = Color(0xFF666666)
        override val grayscale60: Color @Composable get() = Color(0xFF4D4D4D)
        override val grayscale70: Color @Composable get() = Color(0xFF333333)
        override val grayscale80: Color @Composable get() = Color(0xFF1A1A1A)
        override val overlay: Color @Composable get() = Color(0xFFE2E2E9)
    }

    object Dark : ColorScheme {
        override val primary: Color @Composable get() = Color(0xFFaac7ff)
        override val onPrimary: Color @Composable get() = Color(0xFF0a305f)
        override val tertiary: Color @Composable get() = Color(0xFF573e5c)
        override val onTertiary: Color @Composable get() = Color(0xFFfad8fd)
        override val background: Color @Composable get() = Color(0xFF111318)
        override val onBackground: Color @Composable get() = Color(0xFFE2E2E9)
        override val surface: Color @Composable get() = Color(0xFF111318)
        override val onSurface: Color @Composable get() = Color(0xFFE2E2E9)
        override val surfaceContainer: Color @Composable get() = Color(0xFF1D2024)
        override val grayscale10: Color @Composable get() = Color(0xFFCCCCCC)
        override val grayscale20: Color @Composable get() = Color(0xFFB3B3B3)
        override val grayscale30: Color @Composable get() = Color(0xFF999999)
        override val grayscale40: Color @Composable get() = Color(0xFF808080)
        override val grayscale50: Color @Composable get() = Color(0xFF666666)
        override val grayscale60: Color @Composable get() = Color(0xFF4D4D4D)
        override val grayscale70: Color @Composable get() = Color(0xFF333333)
        override val grayscale80: Color @Composable get() = Color(0xFF1A1A1A)
        override val overlay: Color @Composable get() = Color(0xFFE2E2E9)
    }
}

data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceContainer: Color,
    val buttonPrimaryContainer: Color,
    val buttonPrimaryContent: Color,
    val buttonPrimaryDisabledContainer: Color,
    val buttonPrimaryDisabledContent: Color,
    val overlay: Color,
)

@Composable
fun buildThemeColors(colorScheme: ColorScheme) =
    Colors(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        tertiary = colorScheme.tertiary,
        onTertiary = colorScheme.onTertiary,
        background = colorScheme.background,
        onBackground = colorScheme.onBackground,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceContainer = colorScheme.surfaceContainer,
        buttonPrimaryContainer = colorScheme.primary,
        buttonPrimaryContent = colorScheme.onPrimary,
        buttonPrimaryDisabledContainer = colorScheme.grayscale40,
        buttonPrimaryDisabledContent = colorScheme.onPrimary,
        overlay = colorScheme.overlay,
    )

internal val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided!")
}
