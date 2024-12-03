package com.battman.core.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val black: Color @Composable get
    val grayscale10: Color @Composable get
    val grayscale20: Color @Composable get
    val grayscale30: Color @Composable get
    val grayscale40: Color @Composable get
    val grayscale50: Color @Composable get
    val grayscale60: Color @Composable get
    val grayscale70: Color @Composable get
    val grayscale80: Color @Composable get
    val white: Color @Composable get
    val orange: Color @Composable get
}

object ColorSchemes {
    object Light : ColorScheme {
        override val black: Color @Composable get() = Color(0xFF000000)
        override val grayscale10: Color @Composable get() = Color(0xFFCCCCCC)
        override val grayscale20: Color @Composable get() = Color(0xFFB3B3B3)
        override val grayscale30: Color @Composable get() = Color(0xFF999999)
        override val grayscale40: Color @Composable get() = Color(0xFF808080)
        override val grayscale50: Color @Composable get() = Color(0xFF666666)
        override val grayscale60: Color @Composable get() = Color(0xFF4D4D4D)
        override val grayscale70: Color @Composable get() = Color(0xFF333333)
        override val grayscale80: Color @Composable get() = Color(0xFF1A1A1A)
        override val white: Color @Composable get() = Color(0xFFFFFFFF)
        override val orange: Color @Composable get() = Color(0xFFFF8F00)
    }
}

data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val onPrimaryInverse: Color,
    val onPrimaryVariant: Color,
    val accent: Color,
    val background: Color,
    val onBackground: Color,
    val onBackgroundInverse: Color,
    val onBackgroundVariant: Color,
    val iconPrimary: Color,
    val iconSecondary: Color,
    val floatingButtonContainer: Color,
    val floatingButtonContent: Color,
    val buttonPrimaryContainer: Color,
    val buttonPrimaryContent: Color,
    val buttonPrimaryDisabledContainer: Color,
    val buttonPrimaryDisabledContent: Color,
    val googleLoginButtonContainer: Color,
    val googleLoginButtonContent: Color,
)

@Composable
fun buildThemeColors(colorScheme: ColorScheme) =
    Colors(
        primary = colorScheme.orange,
        onPrimary = colorScheme.white,
        onPrimaryInverse = colorScheme.black,
        onPrimaryVariant = colorScheme.grayscale40,
        accent = colorScheme.orange,
        background = colorScheme.white,
        onBackground = colorScheme.white,
        onBackgroundVariant = colorScheme.grayscale40,
        onBackgroundInverse = colorScheme.black,
        iconPrimary = colorScheme.white,
        iconSecondary = colorScheme.black,
        floatingButtonContainer = colorScheme.grayscale70,
        floatingButtonContent = colorScheme.white,
        buttonPrimaryContainer = colorScheme.orange,
        buttonPrimaryContent = colorScheme.white,
        buttonPrimaryDisabledContainer = colorScheme.grayscale40,
        buttonPrimaryDisabledContent = colorScheme.white,
        googleLoginButtonContainer = colorScheme.black,
        googleLoginButtonContent = colorScheme.white,
    )

internal val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided!")
}
