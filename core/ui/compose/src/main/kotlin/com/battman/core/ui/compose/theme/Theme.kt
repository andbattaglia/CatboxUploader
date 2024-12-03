package com.battman.core.ui.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.battman.core.ui.compose.theme.ColorSchemes.Light

@Composable
fun CatboxUploaderTheme(
    colors: Colors = buildThemeColors(colorScheme = Light),
    dimensions: Dimensions = defaultDimensions,
    typography: Typography = defaultTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
    ) {
        content()
    }
}

object YesDearTheme {
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
