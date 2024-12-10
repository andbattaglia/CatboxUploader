package com.battman.catboxuploader.feature.cloudgallery.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.feature.cloudgallery.ui.CloudGalleryContract.UiState
import com.battman.core.ui.compose.components.CUMessagePage
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.components.CircularIndicatorOverlay
import com.battman.core.ui.compose.components.CuCard
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily
import kotlinx.coroutines.launch
import java.util.Date
import com.battman.catboxuploader.feature.common.R as Rcommon

@Composable
internal fun CloudGalleryScreen(
    viewModel: CloudGalleryModel,
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect {}
    }

    CloudGalleryScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onItemClick = { url ->
            clipboardManager.setText(AnnotatedString(url))

            scope.launch {
                val message = context.getString(R.string.cloud_gallery_snackbar_copied)
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short,
                )
            }
        },
    )
}

@Composable
internal fun CloudGalleryScreen(
    state: UiState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CUTopAppBar(
                title = stringResource(id = R.string.cloud_gallery_title),
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
            is UiState.Error -> {
                CUMessagePage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    title = stringResource(state.titleRes),
                    description = stringResource(state.descriptionRes),
                    painter = painterResource(id = Rcommon.drawable.error_state),
                )
            }
            is UiState.Success -> {
                CloudGalleryContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    photos = state.photos,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@Composable
internal fun CloudGalleryContent(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    Box(modifier = modifier) {
        if (photos.isNotEmpty()) {
            ImageListScreen(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = dimensions.spacing_s,
                    start = dimensions.spacing_s,
                    end = dimensions.spacing_s,
                    bottom = dimensions.spacing_xl + dimensions.button_size_L,
                ),
                photos = photos,
                onItemClick = onItemClick,
            )
        } else {
            CUMessagePage(
                modifier = modifier,
                title = stringResource(Rcommon.string.empty_title),
                painter = painterResource(id = Rcommon.drawable.empty_state),
            )
        }
    }
}

@Composable
fun ImageListScreen(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensions.spacing_xs),
    ) {
        items(photos.size) { index ->
            val photo = photos[index]
            ImageCard(
                modifier = Modifier
                    .fillMaxSize(),
                photo = photo,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
fun ImageCard(
    photo: Photo,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        CuCard(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            shape = RoundedCornerShape(dimensions.roundCorner_S),
            border = BorderStroke(dimensions.thickness_S, colors.primary),
            onClick = { onItemClick(photo.uploadedLink) },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.surfaceContainer),
                contentAlignment = Alignment.BottomCenter,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = photo.contentUri,
                    contentDescription = photo.name,
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.background),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensions.spacing_xs, vertical = dimensions.spacing_m),
                        text = photo.uploadedLink,
                        style = typography.labelLarge,
                        color = colors.onBackground,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CloudGalleryLoadingPreview() {
    CatboxUploaderTheme {
        CloudGalleryScreen(
            state = UiState.Loading,
            snackbarHostState = SnackbarHostState(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CloudGalleryContentPreview() {
    CatboxUploaderTheme {
        CloudGalleryContent(
            photos = (1..7).map {
                Photo(
                    id = it.toLong(),
                    name = "Photo $it",
                    contentUri = "https://www.example.com/photo",
                    data = Date(),
                    uploaded = true,
                    uploadedLink = "https://www.example.com/photo",
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManagePhotosContentEmptyPreview() {
    CatboxUploaderTheme {
        CloudGalleryContent(
            modifier = Modifier
                .fillMaxSize(),
            photos = emptyList(),
        )
    }
}
