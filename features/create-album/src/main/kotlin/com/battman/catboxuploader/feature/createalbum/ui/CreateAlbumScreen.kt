package com.battman.catboxuploader.feature.createalbum.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.battman.catboxuploader.feature.createalbum.ui.CreateAlbumContract.UiState
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.components.CircularIndicatorOverlay
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily

@Composable
internal fun CreateAlbumScreen(
    viewModel: CreateAlbumModel,
) {
    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect {}
    }

    CreateAlbumScreen(
        state = state,
    )
}

@Composable
internal fun CreateAlbumScreen(
    state: UiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        topBar = {
            CUTopAppBar(
                title = stringResource(id = R.string.create_album_title),
                titleColor = colors.onBackground,
                titleStyle = typography.titleLarge,
                titleFontFamily = outfitFontFamily,
                containerColor = colors.background,
                scrolledContainerColor = colors.surface,
                elevation = dimensions.elevation,
            )
        },
    ) { paddingValues ->
        when (state) {
            UiState.Loading -> {
                CircularIndicatorOverlay(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadPhotosLoadingPreview() {
    CatboxUploaderTheme {
        CreateAlbumScreen(
            state = UiState.Loading,
        )
    }
}
