package com.battman.catboxuploader.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.battman.feature.select.country.ui.navigation.SELECT_COUNTRY_ROUTE
import com.battman.feature.select.country.ui.navigation.selectCountryScreen
import com.battman.feature.select.photo.ui.navigation.navigateToSelectPhotos
import com.battman.feature.select.photo.ui.navigation.selectPhotosScreen

@Composable
fun RootHost() {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SELECT_COUNTRY_ROUTE,
    ) {
        selectCountryScreen(
            onNavigateToGallery = { navController.navigateToSelectPhotos() },
        )

        selectPhotosScreen(
            onNavigateToPermissionSettings = { context.openActivitySettings() },
            onNavigateToUpload = { },
        )
    }
}

private fun Context.openActivitySettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null),
    )
    startActivity(intent)
}
