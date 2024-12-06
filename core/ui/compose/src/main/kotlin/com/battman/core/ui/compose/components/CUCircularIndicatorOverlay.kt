package com.battman.core.ui.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions

@Composable
fun CircularIndicatorOverlay(
    modifier: Modifier = Modifier,
    alpha: Float = 0.0f,
    width: Dp = dimensions.indeterminateCircularProgress,
    color: Color = colors.primary,
) {
    Surface(
        color = Color.Black.copy(alpha = alpha),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(width),
                color = color,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndeterminateCircularIndicatorPreview() {
    CircularIndicatorOverlay(
        modifier = Modifier.fillMaxSize(),
        color = Color.Red,
    )
}
