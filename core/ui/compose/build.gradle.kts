plugins {
    id("battman.android.library.compose")
}

android {
    namespace = "com.battman.core.ui.compose"
}

dependencies {
    api(libs.bundles.compose)
}
