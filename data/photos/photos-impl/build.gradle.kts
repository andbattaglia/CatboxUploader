plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.catboxuploader.photos.di"

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://catbox.moe/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://catbox.moe/\"")
        }
    }
}

dependencies {
    api(projects.data.photos.photosApi)

    implementation(projects.core.contentResolver.contentResolverApi)
    implementation(projects.core.dispatcher.dispatcherApi)
    implementation(projects.core.network.networkApi)
    implementation(projects.data.networkExt)

    implementation(libs.javax.inject)
}
