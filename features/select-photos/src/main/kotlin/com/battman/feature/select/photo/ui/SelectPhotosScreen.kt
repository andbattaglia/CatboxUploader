package com.battman.feature.select.photo.ui

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.battman.core.ui.compose.components.CUButton
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
internal fun SelectPhotosScreen(
    viewModel: SelectPhotosModel,
    onNavigateToPermissionSettings: () -> Unit,
) {
    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect { }
    }

    SelectPhotosScreen(
        state = state,
        onNavigateToPermissionSettings = onNavigateToPermissionSettings,
    )
}

@Composable
internal fun SelectPhotosScreen(
    state: UiState,
    modifier: Modifier = Modifier,
    onNavigateToPermissionSettings: () -> Unit = {},
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
            onNavigateToPermissionSettings = onNavigateToPermissionSettings,
            content = {},
        )
    }
}

@Composable
internal fun RequestMediaPermissionView(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToPermissionSettings: () -> Unit = {},
) {
    Box(modifier = modifier) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestMediaPermissionViewTiramisu(
                onNavigateToPermissionSettings = onNavigateToPermissionSettings,
                content = content,
            )
        } else {
            RequestMediaPermissionViewPreTiramisu(content = content)
        }
    }
}

@Composable
internal fun RequestMediaPermissionViewPreTiramisu(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        content()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun RequestMediaPermissionViewTiramisu(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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
            content()
        } else {
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
                    label = "Request permission",
                    onClick = {
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
}

@Preview(showBackground = true)
@Composable
fun SelectPhotosPreview() {
    CatboxUploaderTheme {
        SelectPhotosScreen(
            state = UiState,
        )
    }
}
