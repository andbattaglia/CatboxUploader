plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.mediastore.di"
}

dependencies {
    api(projects.data.mediastore.mediastoreApi)
    api(projects.data.mediastore.mediastoreImpl)
}
