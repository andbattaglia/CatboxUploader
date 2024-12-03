package com.battman.core.ui.compose.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.battman.core.ui.compose.R

val outfitFontFamily = FontFamily(
    Font(resId = R.font.outfit_regular, weight = Normal),
    Font(resId = R.font.outfit_medium, weight = Medium),
    Font(resId = R.font.outfit_semibold, weight = SemiBold),
    Font(resId = R.font.outfit_bold, weight = Bold),
)
