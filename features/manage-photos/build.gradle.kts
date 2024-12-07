plugins {
    id("battman.android.library.compose")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.feature.managephotos.ui"
}

dependencies {
    implementation(projects.core.ui.compose)
    implementation(projects.core.ui.mvi)
    implementation(projects.core.ui.navigation)
    implementation(projects.domain.usecases)
    implementation(projects.features.common)
    implementation(libs.android.image.cropper)
    implementation(libs.coil.compose)
    implementation(libs.google.accompanist.permission)
}
