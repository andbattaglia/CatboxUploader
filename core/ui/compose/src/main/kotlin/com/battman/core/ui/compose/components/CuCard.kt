package com.battman.core.ui.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions

@Composable
fun CuCard(
    modifier: Modifier = Modifier,
    cardContainerColor: Color = colors.surfaceContainer,
    cardContainerRippleColor: Color = colors.primary,
    elevation: Dp = dimensions.elevationNone,
    border: BorderStroke? = null,
    shape: Shape = RoundedCornerShape(dimensions.roundCorner_M),
    showOverlay: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(
                        color = cardContainerRippleColor,
                    ),
                    onClick = onClick,
                ),
            border = border,
            colors = CardDefaults.cardColors(
                containerColor = cardContainerColor,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = elevation,
            ),
            shape = shape,
            content = content,
        )

        if (showOverlay) {
            Box(
                modifier = Modifier
                    .clip(shape)
                    .matchParentSize()
                    .background(colors.overlay.copy(alpha = 0.5f)),
            )
        }
    }
}
