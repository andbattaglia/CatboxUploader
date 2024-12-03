plugins {
    id("battman.android.library.compose")
}

android {
    namespace = "com.battman.core.ui.navigation"
}

dependencies {
    api(libs.bundles.compose.navigation)
}
