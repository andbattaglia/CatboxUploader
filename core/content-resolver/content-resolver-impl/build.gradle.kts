plugins {
    id("battman.android.library")
}

android {
    namespace = "com.battman.core.contentresolver.impl"
}

dependencies {
    api(projects.core.contentResolver.contentResolverApi)

    implementation(libs.javax.inject)
}
