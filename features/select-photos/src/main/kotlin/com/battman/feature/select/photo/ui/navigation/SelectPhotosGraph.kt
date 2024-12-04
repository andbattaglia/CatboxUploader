package com.battman.feature.select.photo.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.feature.select.photo.ui.SelectPhotosScreen

const val SELECT_PHOTOS_ROUTE = "select-photos"

fun NavController.navigateToSelectPhotos() {
    navigate(SELECT_PHOTOS_ROUTE)
}

fun NavGraphBuilder.selectPhotosScreen() {
    composable(route = SELECT_PHOTOS_ROUTE) {
        SelectPhotosScreen(
            viewModel = hiltViewModel(),
        )
    }
}
