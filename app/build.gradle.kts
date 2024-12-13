import com.battman.gradle.plugins.extensions.readProperties

plugins {
    id("battman.android.application")
    id("battman.android.hilt")
}

android {
    namespace = "com.battman.catboxuploader"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.battman.catboxuploader"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val properties = rootProject.readProperties("local.properties")
        create("release") {
            storeFile = rootProject.file(".keystore/release.jks")
            storePassword = properties["release.keystore.password"] as String?
            keyAlias = properties["release.key.alias"] as String?
            keyPassword = properties["release.key.password"] as String?
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)

    implementation(projects.core.contentResolver.contentResolverDi)
    implementation(projects.core.dispatcher.dispatcherDi)
    implementation(projects.core.network.networkDi)
    implementation(projects.core.ui.compose)
    implementation(projects.core.ui.navigation)
    implementation(projects.data.countries.countriesDi)
    implementation(projects.data.photos.photosDi)
    implementation(projects.features.cloudGallery)
    implementation(projects.features.managePhotos)
    implementation(projects.features.selectCountry)
    implementation(projects.features.selectPhotos)

    implementation(libs.bundles.compose)
}
