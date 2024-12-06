plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.catboxuploader.mediastore.di"
}

dependencies {
    api(projects.data.mediastore.mediastoreApi)

    implementation(libs.javax.inject)
    implementation(libs.dagger.hilt.android)
}
