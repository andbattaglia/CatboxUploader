package com.battman.core.ui.compose.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CUTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    titleFontFamily: FontFamily? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Dp = dimensions.elevationNone,
) {
    TopAppBar(
        modifier = modifier
            .shadow(elevation = elevation),
        title = {
            Text(
                text = title,
                color = titleColor,
                style = titleStyle,
                fontFamily = titleFontFamily,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor,
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    )
}
