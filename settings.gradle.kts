pluginManagement {
    includeBuild("gradle/plugins")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "dagger.hilt.android.plugin" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CatboxUploader"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":app",
    ":core:dispatcher:dispatcher-api",
    ":core:dispatcher:dispatcher-di",
    ":core:network:network-api",
    ":core:network:network-di",
    ":core:network:network-test",
    ":core:ui:compose",
    ":core:ui:mvi",
    ":core:ui:navigation",
    ":core:test:test-jvm",
    ":core:usecase:usecase-api",
    ":data:countries:countries-api",
    ":data:countries:countries-di",
    ":data:countries:countries-impl",
    ":data:mediastore:mediastore-api",
    ":data:mediastore:mediastore-di",
    ":data:mediastore:mediastore-impl",
    ":data:network-ext",
    ":domain:models",
    ":domain:usecases",
    ":features:common",
    ":features:select-country",
    ":features:select-photos",
    ":features:create-album"
)
