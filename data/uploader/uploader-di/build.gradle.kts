plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.uploader.di"
}

dependencies {
    api(projects.data.uploader.uploaderApi)
    api(projects.data.uploader.uploaderImpl)

    implementation(projects.core.network.networkApi)
}
