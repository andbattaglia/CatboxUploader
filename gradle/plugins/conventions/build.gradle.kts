plugins {
    `kotlin-dsl`
}

group = "com.battman.gradle.plugins.conventions"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.spotless)
}

gradlePlugin {
    plugins {
        register("KotlinJvmPlugin") {
            id = "battman.jvm.library"
            implementationClass = "KotlinJvmPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "battman.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "battman.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("AndroidLibraryUiPlugin") {
            id = "battman.android.library.compose"
            implementationClass = "AndroidLibraryUiPlugin"
        }
        register("AndroidApplicationPlugin") {
            id = "battman.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}
