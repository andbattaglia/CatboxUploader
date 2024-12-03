plugins {
    id("battman.android.library.compose")
}

android {
    namespace = "com.battman.core.mvi"
}

dependencies {
    implementation(libs.bundles.compose.lifecycle)
}
