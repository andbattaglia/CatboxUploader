plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.core.network.di"

    buildFeatures.buildConfig = true
}

dependencies {
    api(projects.core.network.networkApi)
}
