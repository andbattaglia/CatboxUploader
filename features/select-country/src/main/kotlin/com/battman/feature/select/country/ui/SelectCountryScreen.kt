package com.battman.feature.select.country.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.battman.catboxuploader.domain.models.Country
import com.battman.core.ui.compose.components.CUMessagePage
import com.battman.core.ui.compose.components.CUTopAppBar
import com.battman.core.ui.compose.components.CircularIndicatorOverlay
import com.battman.core.ui.compose.components.CuCard
import com.battman.core.ui.compose.theme.CatboxUploaderTheme
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.colors
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.dimensions
import com.battman.core.ui.compose.theme.CatboxUploaderTheme.typography
import com.battman.core.ui.compose.theme.outfitFontFamily
import com.battman.feature.select.country.ui.SelectCountryContract.UiEvent.NavigateToGallery
import com.battman.feature.select.country.ui.SelectCountryContract.UiIntent.OnCountryClick
import com.battman.feature.select.country.ui.SelectCountryContract.UiState
import com.battman.catboxuploader.feature.common.R as Rcommon

@Composable
internal fun SelectCountryScreen(
    viewModel: SelectCountryModel,
    navigateToGallery: () -> Unit,
) {
    val (state, events, processIntent) = viewModel

    LaunchedEffect(key1 = Unit) {
        events.collect { event ->
            when (event) {
                is NavigateToGallery -> {
                    navigateToGallery()
                }
            }
        }
    }

    SelectCountryScreen(
        state = state,
        onClick = { iso ->
            processIntent(OnCountryClick(iso))
        },
    )
}

@Composable
internal fun SelectCountryScreen(
    state: UiState,
    modifier: Modifier = Modifier,
    onClick: (iso: Int) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        topBar = {
            CUTopAppBar(
                title = stringResource(id = R.string.select_country_title),
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
                        .fillMaxSize(),
                )
            }
            is UiState.Success -> {
                SelectCountryContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    countries = state.countries,
                    onClick = onClick,
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
        }
    }
}

@Composable
internal fun SelectCountryContent(
    countries: List<Country>,
    modifier: Modifier = Modifier,
    onClick: (iso: Int) -> Unit = {},
) {
    if (countries.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(dimensions.spacing_xs),
        ) {
            items(countries.size) { index ->
                CountryItem(
                    country = countries[index],
                    onClick = onClick,
                )
            }
        }
    } else {
        CUMessagePage(
            modifier = modifier,
            title = stringResource(Rcommon.string.empty_title),
            painter = painterResource(id = Rcommon.drawable.empty_state),
        )
    }
}

@Composable
internal fun CountryItem(
    country: Country,
    modifier: Modifier = Modifier,
    onClick: (iso: Int) -> Unit = {},
) {
    CuCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensions.spacing_xs),
        onClick = { onClick(country.iso) },
    ) {
        Column(
            modifier = Modifier.padding(dimensions.spacing_m),
        ) {
            Text(
                text = country.name,
                color = colors.primary,
                style = typography.bodyLarge,
                fontFamily = outfitFontFamily,
            )
            Spacer(modifier = Modifier.height(dimensions.spacing_xs))
            Text(
                text = stringResource(R.string.select_country_iso_code, country.isoAlpha3),
                color = colors.onSurface,
                style = typography.labelSmall,
                fontFamily = outfitFontFamily,
            )
            Text(
                text = stringResource(R.string.select_country_phone_prefix, country.phonePrefix),
                color = colors.onSurface,
                style = typography.labelSmall,
                fontFamily = outfitFontFamily,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCountrySuccessPreview() {
    val countries = listOf(
        Country(248, "AX", "ALA", "Aland Islands", "+358-18", "^[0-9]{8,15}$"),
        Country(840, "US", "USA", "United States", "+1", "^[0-9]{10}$"),
        Country(124, "CA", "CAN", "Canada", "+1", "^[0-9]{10}$"),
        Country(392, "JP", "JPN", "Japan", "+81", "^[0-9]{9,10}$"),
    )

    CatboxUploaderTheme {
        SelectCountryScreen(
            state = UiState.Success(countries),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCountryErrorPreview() {
    CatboxUploaderTheme {
        SelectCountryScreen(
            state = UiState.Error(
                titleRes = Rcommon.string.error_network_title,
                descriptionRes = Rcommon.string.error_network_description,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCountryEmptyPreview() {
    val countries = emptyList<Country>()

    CatboxUploaderTheme {
        SelectCountryScreen(
            state = UiState.Success(countries),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCountryLoadingPreview() {
    CatboxUploaderTheme {
        SelectCountryScreen(
            state = UiState.Loading,
        )
    }
}
