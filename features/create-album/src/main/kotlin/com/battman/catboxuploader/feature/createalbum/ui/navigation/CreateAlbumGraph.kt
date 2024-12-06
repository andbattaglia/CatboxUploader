package com.battman.catboxuploader.feature.createalbum.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.catboxuploader.feature.createalbum.ui.CreateAlbumScreen

const val CREATE_ALBUM_ROUTE = "create-album"

fun NavController.navigateToCreateAlbum() {
    navigate(CREATE_ALBUM_ROUTE)
}

fun NavGraphBuilder.createAlbumScreen(
    onNavigateToGallery: () -> Unit,
) {
    composable(route = CREATE_ALBUM_ROUTE) {
        CreateAlbumScreen(
            viewModel = hiltViewModel(),
        )
    }
}
