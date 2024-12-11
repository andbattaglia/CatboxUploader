package com.battman.feature.select.photo.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.battman.core.ui.compose.components.CUButton
import com.battman.core.ui.compose.components.CUMessagePage
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.components.CircularIndicatorOverlay
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiEvent.NavigateToUpload
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnItemSelected
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnNext
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnPermissionGranted
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiState
import com.battman.feature.select.photo.ui.models.UIPhoto
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.util.Date
import com.battman.catboxuploader.feature.common.R as Rcommon

@Composable
internal fun SelectPhotosScreen(
    viewModel: SelectPhotosModel,
    onNavigateToPermissionSettings: () -> Unit,
    onNavigateToUpload: () -> Unit,
) {
    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect { events ->
            when (events) {
                NavigateToUpload -> {
                    onNavigateToUpload()
                }
            }
        }
    }

    SelectPhotosScreen(
        state = state,
        onPermissionGranted = { processIntent(OnPermissionGranted) },
        onNavigateToPermissionSettings = onNavigateToPermissionSettings,
        onItemSelected = { photoId -> processIntent(OnItemSelected(photoId)) },
        onNextClick = { processIntent(OnNext) },
    )
}

@Composable
internal fun SelectPhotosScreen(
    state: UiState,
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit = {},
    onNavigateToPermissionSettings: () -> Unit = {},
    onItemSelected: (Long) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        topBar = {
            CUTopAppBar(
                title = stringResource(id = R.string.select_photos_title),
                titleColor = colors.onBackground,
                titleStyle = typography.titleLarge,
                titleFontFamily = outfitFontFamily,
                containerColor = colors.background,
                scrolledContainerColor = colors.surface,
                elevation = dimensions.elevation,
            )
        },
    ) { paddingValues ->
        RequestMediaPermissionView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onPermissionGranted = onPermissionGranted,
            onNavigateToPermissionSettings = onNavigateToPermissionSettings,
            content = {
                when (state) {
                    is UiState.Loading -> {
                        CircularIndicatorOverlay(
                            modifier = Modifier
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
                        SelectPhotosContent(
                            modifier = Modifier.fillMaxSize(),
                            photos = state.photos,
                            nextButtonEnabled = state.nextButtonEnabled,
                            onItemSelected = onItemSelected,
                            onNextClick = onNextClick,
                        )
                    }
                }
            },
        )
    }
}

@Composable
internal fun SelectPhotosContent(
    photos: List<UIPhoto>,
    nextButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onItemSelected: (Long) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    Box(modifier = modifier) {
        if (photos.isNotEmpty()) {
            ImageGridScreen(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = dimensions.spacing_s,
                    start = dimensions.spacing_s,
                    end = dimensions.spacing_s,
                    bottom = dimensions.spacing_xl + dimensions.button_size_L,
                ),
                photos = photos,
                onItemSelected = onItemSelected,
            )
        } else {
            CUMessagePage(
                modifier = modifier,
                title = stringResource(Rcommon.string.empty_title),
                painter = painterResource(id = Rcommon.drawable.empty_state),
            )
        }
        CUButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(dimensions.spacing_m),
            label = stringResource(R.string.select_photos_next_button),
            enabled = nextButtonEnabled,
            onClick = { onNextClick() },
        )
    }
}

@Composable
internal fun RequestMediaPermissionView(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit = {},
    onNavigateToPermissionSettings: () -> Unit = {},
) {
    Box(modifier = modifier) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestMediaPermissionViewTiramisu(
                onNavigateToPermissionSettings = onNavigateToPermissionSettings,
                onPermissionGranted = onPermissionGranted,
                content = content,
            )
        } else {
            RequestMediaPermissionViewPreTiramisu(
                onNavigateToPermissionSettings = onNavigateToPermissionSettings,
                onPermissionGranted = onPermissionGranted,
                content = content,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun RequestMediaPermissionViewPreTiramisu(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit = {},
    onNavigateToPermissionSettings: () -> Unit = {},
) {
    val view = LocalView.current
    val readStoragePermissionState =
        if (view.isInEditMode) {
            null
        } else {
            rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    Box(modifier = modifier) {
        if (readStoragePermissionState?.status?.isGranted == true) {
            LaunchedEffect(Unit) { onPermissionGranted() }
            content()
        } else {
            RequestMediaPermission(
                modifier = Modifier.fillMaxSize(),
                onRequestClick = {
                    if (readStoragePermissionState?.status?.shouldShowRationale == true) {
                        onNavigateToPermissionSettings()
                    } else {
                        readStoragePermissionState?.launchPermissionRequest()
                    }
                },
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun RequestMediaPermissionViewTiramisu(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit = {},
    onNavigateToPermissionSettings: () -> Unit = {},
) {
    val view = LocalView.current
    val readStoragePermissionState =
        if (view.isInEditMode) {
            null
        } else {
            rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)
        }

    Box(modifier = modifier) {
        if (readStoragePermissionState?.status?.isGranted == true) {
            LaunchedEffect(Unit) { onPermissionGranted() }
            content()
        } else {
            RequestMediaPermission(
                modifier = Modifier.fillMaxSize(),
                onRequestClick = {
                    if (readStoragePermissionState?.status?.shouldShowRationale == true) {
                        onNavigateToPermissionSettings()
                    } else {
                        readStoragePermissionState?.launchPermissionRequest()
                    }
                },
            )
        }
    }
}

@Composable
fun RequestMediaPermission(
    modifier: Modifier = Modifier,
    onRequestClick: () -> Unit = {},
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = dimensions.spacing_l),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.select_photos_permission_title),
            textAlign = TextAlign.Center,
            color = colors.onBackground,
            style = typography.titleLarge,
            fontFamily = outfitFontFamily,
        )
        Spacer(modifier = Modifier.height(dimensions.spacing_m))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.select_photos_permission_description),
            textAlign = TextAlign.Center,
            color = colors.onBackground,
            style = typography.bodyMedium,
            fontFamily = outfitFontFamily,
        )
        Spacer(modifier = Modifier.height(dimensions.spacing_m))
        CUButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.select_photos_permission_button),
            onClick = onRequestClick,
        )
    }
}

@Composable
fun ImageGridScreen(
    photos: List<UIPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemSelected: (Long) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(dimensions.spacing_xs),
        verticalArrangement = Arrangement.spacedBy(dimensions.spacing_xs),
    ) {
        items(photos.size) { index ->
            val photo = photos[index]
            ImageCard(
                modifier = Modifier
                    .fillMaxSize(),
                photo = photo,
                onItemSelected = onItemSelected,
            )
        }
    }
}

@Composable
fun ImageCard(
    photo: UIPhoto,
    modifier: Modifier = Modifier,
    onItemSelected: (Long) -> Unit = {},
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(colors.surfaceContainer)
            .border(
                width = if (photo.selected) dimensions.thickness_L else 0.dp,
                color = if (photo.selected) colors.primary else colors.surfaceContainer,
            )
            .clickable { onItemSelected(photo.id) },
    ) {
        val imageRequest = ImageRequest.Builder(context)
            .data(photo.contentUri)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()

        val painter = rememberAsyncImagePainter(imageRequest, filterQuality = FilterQuality.Low)

        Image(
            painter = painter,
            contentDescription = photo.name,
            contentScale = ContentScale.Crop,
        )

        if (photo.selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(colors.overlay.copy(alpha = 0.5f)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPhotosScreenPreview() {
    CatboxUploaderTheme {
        SelectPhotosScreen(
            state = UiState.Success(
                photos = emptyList(),
                nextButtonEnabled = false,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPhotosContentPreview() {
    CatboxUploaderTheme {
        val photos = (1..100).map {
            UIPhoto(
                id = it.toLong(),
                name = "Photo $it",
                contentUri = Uri.parse("https://www.example.com/photo"),
                data = Date(),
                selected = true,
            )
        }

        SelectPhotosContent(
            photos = photos,
            nextButtonEnabled = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPhotosContentEmptyPreview() {
    CatboxUploaderTheme {
        val photos = emptyList<UIPhoto>()

        SelectPhotosContent(
            photos = photos,
            nextButtonEnabled = false,
        )
    }
}
