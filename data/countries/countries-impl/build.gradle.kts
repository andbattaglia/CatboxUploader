plugins {
    id("battman.android.library")
}

android {
    namespace = "com.battman.catboxuploader.countries.di"
}

dependencies {
    api(projects.data.countries.countriesApi)

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization)
}
