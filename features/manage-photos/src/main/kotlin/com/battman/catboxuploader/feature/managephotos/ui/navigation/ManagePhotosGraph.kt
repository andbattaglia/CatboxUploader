package com.battman.catboxuploader.feature.managephotos.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosScreen

const val MANAGE_PHOTOS_ROUTE = "manage-photos"

fun NavController.navigateToManagePhotos() {
    navigate(MANAGE_PHOTOS_ROUTE)
}

fun NavGraphBuilder.managePhotosScreen() {
    composable(route = MANAGE_PHOTOS_ROUTE) {
        ManagePhotosScreen(
            viewModel = hiltViewModel(),
        )
    }
}
