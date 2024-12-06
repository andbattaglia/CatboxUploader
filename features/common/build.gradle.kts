plugins {
    id("battman.android.library.compose")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.feature.common"
}

dependencies {
    implementation(projects.core.ui.compose)
    implementation(projects.domain.models)
}
