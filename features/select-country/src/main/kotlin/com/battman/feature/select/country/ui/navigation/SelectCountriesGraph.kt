package com.battman.feature.select.country.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.battman.feature.select.country.ui.SelectCountryScreen

const val SELECT_COUNTRY_ROUTE = "select-country"

fun NavController.navigateToSelectCountry() {
    navigate(SELECT_COUNTRY_ROUTE)
}

fun NavGraphBuilder.selectCountryScreen(
    onNavigateToGallery: () -> Unit,
) {
    composable(route = SELECT_COUNTRY_ROUTE) {
        SelectCountryScreen(
            viewModel = hiltViewModel(),
            navigateToGallery = onNavigateToGallery,
        )
    }
}
