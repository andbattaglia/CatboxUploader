plugins {
    id("battman.android.library.compose")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.feature.select.country.ui"
}

dependencies {
    implementation(projects.core.ui.compose)
    implementation(projects.core.ui.mvi)
    implementation(projects.core.ui.navigation)
    implementation(projects.domain.usecases)
}
