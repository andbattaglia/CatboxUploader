plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.core.contentresolver.di"
}

dependencies {
    api(projects.core.contentResolver.contentResolverApi)
    api(projects.core.contentResolver.contentResolverImpl)
}
