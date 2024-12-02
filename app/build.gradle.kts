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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.bundles.compose)
}
