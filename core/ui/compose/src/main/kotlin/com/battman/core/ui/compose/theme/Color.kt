package com.battman.core.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val primary: Color @Composable get
    val onPrimary: Color @Composable get
    val background: Color @Composable get
    val onBackground: Color @Composable get
    val surface: Color @Composable get
    val onSurface: Color @Composable get
    val surfaceContainer: Color @Composable get
}

object ColorSchemes {
    object Light : ColorScheme {
        override val primary: Color @Composable get() = Color(0xFF415F91)
        override val onPrimary: Color @Composable get() = Color(0xFFFFFFFF)
        override val background: Color @Composable get() = Color(0xFFFFFFFF)
        override val onBackground: Color @Composable get() = Color(0xFF191C20)
        override val surface: Color @Composable get() = Color(0xFFF9F9FF)
        override val onSurface: Color @Composable get() = Color(0xFF191C20)
        override val surfaceContainer: Color @Composable get() = Color(0xFFEDEDF4)
    }

    object Dark : ColorScheme {
        override val primary: Color @Composable get() = Color(0xFFaac7ff)
        override val onPrimary: Color @Composable get() = Color(0xFF0a305f)
        override val background: Color @Composable get() = Color(0xFF111318)
        override val onBackground: Color @Composable get() = Color(0xFFE2E2E9)
        override val surface: Color @Composable get() = Color(0xFF111318)
        override val onSurface: Color @Composable get() = Color(0xFFE2E2E9)
        override val surfaceContainer: Color @Composable get() = Color(0xFF1D2024)
    }
}

data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceContainer: Color,
)

@Composable
fun buildThemeColors(colorScheme: ColorScheme) =
    Colors(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        background = colorScheme.background,
        onBackground = colorScheme.onBackground,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceContainer = colorScheme.surfaceContainer,
    )

internal val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided!")
}
