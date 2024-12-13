package com.battman.feature.select.photo.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.core.ui.navigation.popUpToTop
import com.battman.feature.select.photo.ui.SelectPhotosScreen

const val SELECT_PHOTOS_ROUTE = "select-photos"

fun NavController.navigateToSelectPhotos() {
    navigate(SELECT_PHOTOS_ROUTE) {
        popUpToTop(this@navigateToSelectPhotos)
    }
}

fun NavGraphBuilder.selectPhotosScreen(
    onNavigateToPermissionSettings: () -> Unit,
    onNavigateToUpload: () -> Unit,
) {
    composable(route = SELECT_PHOTOS_ROUTE) {
        SelectPhotosScreen(
            viewModel = hiltViewModel(),
            onNavigateToPermissionSettings = onNavigateToPermissionSettings,
            onNavigateToUpload = onNavigateToUpload,
        )
    }
}
