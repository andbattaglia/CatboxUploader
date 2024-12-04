package com.battman.core.ui.compose.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.battman.core.ui.compose.theme.ColorSchemes.Dark
import com.battman.core.ui.compose.theme.ColorSchemes.Light

@Composable
fun CatboxUploaderTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dimensions: Dimensions = defaultDimensions,
    typography: Typography = defaultTypography,
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) buildThemeColors(colorScheme = Dark) else buildThemeColors(colorScheme = Light)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
    ) {
        content()
    }
}

object CatboxUploaderTheme {
    val colors: Colors
        @Composable
        get() = LocalColors.current

    val dimensions: Dimensions
        @Composable
        get() = LocalDimensions.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}
