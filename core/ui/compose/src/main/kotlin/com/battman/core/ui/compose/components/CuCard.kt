package com.battman.core.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions

@Composable
fun CuCard(
    modifier: Modifier = Modifier,
    cardContainerColor: Color = colors.surfaceContainer,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(dimensions.roundCorner_M)
    Card(
        modifier = modifier
            .clip(shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick,
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardContainerColor,
        ),
        shape = shape,
        content = content,
    )
}
