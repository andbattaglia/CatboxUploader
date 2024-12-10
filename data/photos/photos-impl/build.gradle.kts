import com.battman.gradle.plugins.extensions.readProperties

plugins {
    id("battman.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.battman.catboxuploader.photos.di"

    buildFeatures.buildConfig = true

    buildTypes {
        val properties = rootProject.readProperties("local.properties")
        debug {
            buildConfigField("String", "BASE_URL", "\"https://catbox.moe/\"")
            buildConfigField("String", "CATBOX_USER_HASH", properties["catbox_user_hash"] as String? ?: "\"\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://catbox.moe/\"")
            buildConfigField("String", "CATBOX_USER_HASH", "\"\"")
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
    implementation(libs.dagger.hilt.android)
    implementation(libs.compressor)
}
