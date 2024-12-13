package com.battman.catboxuploader.feature.cloudgallery.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.catboxuploader.feature.cloudgallery.ui.CloudGalleryScreen
import com.battman.core.ui.navigation.popUpToTop

const val CLOUD_GALLERY_ROUTE = "cloud-gallery"

fun NavController.navigateToCloudGallery() {
    navigate(CLOUD_GALLERY_ROUTE) {
        popUpToTop(this@navigateToCloudGallery)
    }
}

fun NavGraphBuilder.cloudGalleryScreen() {
    composable(route = CLOUD_GALLERY_ROUTE) {
        CloudGalleryScreen(
            viewModel = hiltViewModel(),
        )
    }
}
