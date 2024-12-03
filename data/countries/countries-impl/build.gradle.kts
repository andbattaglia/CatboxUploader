plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.catboxuploader.countries.di"

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.photoforse.online/\"")
            buildConfigField("String", "API_KEY", "\"AIzaSyCccmdkjGe_9Yt-INL2rCJTNgoS4CXsRDc\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.photoforse.online/\"")
            buildConfigField("String", "API_KEY", "\"AIzaSyCccmdkjGe_9Yt-INL2rCJTNgoS4CXsRDc\"")
        }
    }
}

dependencies {
    api(projects.data.countries.countriesApi)

    implementation(projects.core.dispatcher.dispatcherApi)
    implementation(projects.core.network.networkApi)
    implementation(projects.data.networkExt)

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization)

    testImplementation(projects.core.network.networkTest)
}
