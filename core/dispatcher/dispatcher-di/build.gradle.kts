plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.core.dispatcher.di"
}

dependencies {
    api(projects.core.dispatcher.dispatcherApi)
}
