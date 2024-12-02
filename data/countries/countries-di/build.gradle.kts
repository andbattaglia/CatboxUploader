plugins {
    id("battman.android.library")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader.countries.di"
}

dependencies {
    api(projects.data.countries.countriesApi)
    api(projects.data.countries.countriesImpl)
}
