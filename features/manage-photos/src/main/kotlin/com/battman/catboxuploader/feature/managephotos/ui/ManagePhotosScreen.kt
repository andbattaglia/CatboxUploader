package com.battman.catboxuploader.feature.managephotos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnDeleteClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnEditClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnUploadClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiState
import com.battman.core.ui.compose.components.CUButton
import com.battman.core.ui.compose.components.CUMessagePage
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.components.CircularIndicatorOverlay
import com.battman.core.ui.compose.components.CuCard
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily
import java.util.Date
import com.battman.catboxuploader.feature.common.R as Rcommon

@Composable
internal fun ManagePhotosScreen(
    viewModel: ManagePhotosModel,
) {
    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect {}
    }

    ManagePhotosScreen(
        state = state,
        onUploadClick = { processIntent(OnUploadClick) },
        onEditClick = { photoId -> processIntent(OnEditClick(photoId)) },
        onDeleteClick = { photoId -> processIntent(OnDeleteClick(photoId)) },
    )
}

@Composable
internal fun ManagePhotosScreen(
    state: UiState,
    modifier: Modifier = Modifier,
    onUploadClick: () -> Unit = {},
    onEditClick: (Long) -> Unit = {},
    onDeleteClick: (Long) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        topBar = {
            CUTopAppBar(
                title = stringResource(id = R.string.manage_photos_title),
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
                ManagePhotosContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    photos = state.photos,
                    onItemSelected = {},
                    onUploadClick = onUploadClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@Composable
internal fun ManagePhotosContent(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    onItemSelected: (Long) -> Unit = {},
    onUploadClick: () -> Unit = {},
    onEditClick: (Long) -> Unit = {},
    onDeleteClick: (Long) -> Unit = {},
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
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
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
            label = stringResource(R.string.manage_photos_button_upload),
            onClick = { onUploadClick() },
        )
    }
}

@Composable
fun ImageGridScreen(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemSelected: (Long) -> Unit = {},
    onEditClick: (Long) -> Unit = {},
    onDeleteClick: (Long) -> Unit = {},
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
                onItemSelected = onItemSelected,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }
}

@Composable
fun ImageCard(
    photo: Photo,
    modifier: Modifier = Modifier,
    onItemSelected: (Long) -> Unit = {},
    onEditClick: (Long) -> Unit = {},
    onDeleteClick: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier.padding(dimensions.spacing_xs),
            text = photo.name,
            style = typography.labelLarge,
            color = colors.onBackground,
        )
        CuCard(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            shape = RoundedCornerShape(dimensions.roundCorner_S),
            onClick = { onItemSelected(photo.id) },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.surfaceContainer),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.spacing_xl),
                    model = photo.contentUri,
                    contentDescription = photo.name,
                    contentScale = ContentScale.Crop,
                )

                Row(
                    modifier = Modifier
                        .padding(
                            start = dimensions.spacing_xl,
                            end = dimensions.spacing_xxl,
                            bottom = dimensions.spacing_2xs,
                        )
                        .align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.spacedBy(dimensions.spacing_s),
                ) {
                    RoundIconButton(
                        painter = painterResource(id = Rcommon.drawable.ic_edit_24),
                        iconTint = colors.onTertiary,
                        backgroundColor = colors.tertiary,
                        onClick = { onEditClick(photo.id) },
                    )
                    RoundIconButton(
                        painter = painterResource(id = Rcommon.drawable.ic_delete_24),
                        iconTint = colors.onTertiary,
                        backgroundColor = colors.tertiary,
                        onClick = { onDeleteClick(photo.id) },
                    )
                }
            }
        }
    }
}

@Composable
fun RoundIconButton(
    onClick: () -> Unit,
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    backgroundColor: Color = colors.primary,
    iconTint: Color = Color.White,
    size: Dp = dimensions.floating_button_m,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = iconTint,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManagePhotosLoadingPreview() {
    CatboxUploaderTheme {
        ManagePhotosScreen(
            state = UiState.Loading,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManagePhotosContentPreview() {
    CatboxUploaderTheme {
        ManagePhotosContent(
            photos = (1..7).map {
                Photo(
                    id = it.toLong(),
                    name = "Photo $it",
                    contentUri = "https://www.example.com/photo",
                    data = Date(),
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManagePhotosContentEmptyPreview() {
    CatboxUploaderTheme {
        ManagePhotosContent(
            modifier = Modifier
                .fillMaxSize(),
            photos = emptyList(),
        )
    }
}
