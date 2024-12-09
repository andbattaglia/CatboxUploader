package com.battman.core.ui.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val spacing_3xs: Dp,
    val spacing_2xs: Dp,
    val spacing_xs: Dp,
    val spacing_s: Dp,
    val spacing_m: Dp,
    val spacing_l: Dp,
    val spacing_xl: Dp,
    val spacing_xxl: Dp,
    val icon_s: Dp,
    val icon_m: Dp,
    val icon_l: Dp,
    val icon_xl: Dp,
    val icon_xxl: Dp,
    val floating_button_m: Dp,
    val roundCorner_S: Dp,
    val roundCorner_M: Dp,
    val roundCorner_L: Dp,
    val thickness_S: Dp,
    val thickness_M: Dp,
    val thickness_L: Dp,
    val stroke_S: Dp,
    val stroke_M: Dp,
    val stroke_L: Dp,
    val button_size_L: Dp,
    val elevation: Dp,
    val elevationNone: Dp = 0.dp,
    val minimumToolbarHeight: Dp = 52.dp,
    val indeterminateCircularProgress: Dp = 52.dp,
    val updateCircularProgress: Dp,
)

internal val defaultDimensions = Dimensions(
    spacing_3xs = 2.dp,
    spacing_2xs = 4.dp,
    spacing_xs = 8.dp,
    spacing_s = 12.dp,
    spacing_m = 16.dp,
    spacing_l = 24.dp,
    spacing_xl = 32.dp,
    spacing_xxl = 48.dp,
    icon_s = 24.dp,
    icon_m = 36.dp,
    icon_l = 72.dp,
    icon_xl = 124.dp,
    icon_xxl = 248.dp,
    floating_button_m = 56.dp,
    roundCorner_S = 8.dp,
    roundCorner_M = 18.dp,
    roundCorner_L = 24.dp,
    thickness_S = 1.dp,
    thickness_M = 2.dp,
    thickness_L = 4.dp,
    stroke_S = 4.dp,
    stroke_M = 8.dp,
    stroke_L = 16.dp,
    button_size_L = 60.dp,
    elevation = 4.dp,
    updateCircularProgress = 150.dp,
)

internal val LocalDimensions = staticCompositionLocalOf {
    defaultDimensions
}
