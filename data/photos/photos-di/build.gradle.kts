plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.photos.di"
}

dependencies {
    api(projects.data.photos.photosApi)
    api(projects.data.photos.photosImpl)

    implementation(projects.core.network.networkApi)
}
