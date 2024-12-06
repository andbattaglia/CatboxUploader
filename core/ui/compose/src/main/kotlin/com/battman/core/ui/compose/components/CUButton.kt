package com.battman.core.ui.compose.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily

@Composable
fun CUButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .height(dimensions.button_size_L),
        colors = ButtonDefaults.buttonColors(
            contentColor = colors.buttonPrimaryContent,
            containerColor = colors.buttonPrimaryContainer,
            disabledContainerColor = colors.buttonPrimaryDisabledContainer,
            disabledContentColor = colors.buttonPrimaryDisabledContent,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(dimensions.roundCorner_M),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier
                .padding(dimensions.spacing_xs),
            fontFamily = outfitFontFamily,
            style = typography.headlineSmall,
            text = label,
        )
    }
}

@Preview
@Composable
internal fun CUButtonPreview() {
    CatboxUploaderTheme {
        CUButton(
            label = "Button",
            onClick = {},
        )
    }
}
