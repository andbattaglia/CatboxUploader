package com.battman.catboxuploader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.battman.feature.select.country.ui.navigation.SELECT_COUNTRY_ROUTE
import com.battman.feature.select.country.ui.navigation.selectCountryScreen

@Composable
fun RootHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SELECT_COUNTRY_ROUTE,
    ) {
        selectCountryScreen(
            onNavigateToGallery = {},
        )
    }
}
