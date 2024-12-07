plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.catboxuploader.uploader.impl"

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
    api(projects.data.uploader.uploaderApi)

    implementation(projects.core.dispatcher.dispatcherApi)
    implementation(projects.core.network.networkApi)
    implementation(projects.data.networkExt)

    implementation(libs.dagger.hilt.android)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization)

    testImplementation(projects.core.network.networkTest)
}
