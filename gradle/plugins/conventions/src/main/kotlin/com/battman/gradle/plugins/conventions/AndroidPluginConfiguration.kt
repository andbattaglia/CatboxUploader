package com.battman.gradle.plugins.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.battman.gradle.plugins.extensions.versionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

private const val COMPILE_SDK = 35
private const val TARGET_DSK = 35
private const val MIN_SDK = 26

internal fun Project.configureAndroidApplication()  {
    project.extensions.getByType(ApplicationExtension::class.java).apply {
        compileSdk = COMPILE_SDK
        defaultConfig {
            minSdk = MIN_SDK
            targetSdk = TARGET_DSK
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        dependencies {
            "implementation"(platform(versionCatalog().findLibrary("androidx.compose.bom").get()))
        }
    }
}

internal fun Project.configureAndroidLibrary()  {
    project.extensions.getByType(LibraryExtension::class.java).apply {
        compileSdk = COMPILE_SDK
        defaultConfig {
            minSdk = MIN_SDK
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}

internal fun Project.configureAndroidLibraryWithCompose()  {
    project.extensions.getByType(LibraryExtension::class.java).apply {
        compileSdk = COMPILE_SDK
        defaultConfig {
            minSdk = MIN_SDK
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        dependencies {
            "implementation"(platform(versionCatalog().findLibrary("androidx.compose.bom").get()))
        }
    }
}
