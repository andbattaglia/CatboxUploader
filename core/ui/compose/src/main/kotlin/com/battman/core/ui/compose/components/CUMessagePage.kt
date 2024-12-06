package com.battman.core.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily

@Composable
fun CUMessagePage(
    title: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    Column(
        modifier = modifier
            .padding(dimensions.spacing_m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(dimensions.icon_xxl),
        )

        Spacer(modifier = Modifier.height(dimensions.spacing_m))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            color = colors.onBackground,
            style = typography.titleLarge,
            fontFamily = outfitFontFamily,
        )

        Spacer(modifier = Modifier.height(dimensions.spacing_m))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            textAlign = TextAlign.Center,
            color = colors.onBackground,
            style = typography.bodyMedium,
            fontFamily = outfitFontFamily,
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun CUMessagePagePreview() {
    CatboxUploaderTheme {
        CUMessagePage(
            modifier = Modifier.fillMaxSize(),
            title = "Lorem Ipsum",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. . Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            painter = painterResource(id = android.R.drawable.ic_delete),
        )
    }
}
