plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.core.network.api"

    buildFeatures.buildConfig = true
}

dependencies {
    api(libs.arrow.core)
    api(libs.arrow.core.retrofit)
    api(libs.kotlinx.serialization)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    api(libs.retrofit)
    api(libs.retrofit.serialization)

    implementation(libs.javax.inject)
}
