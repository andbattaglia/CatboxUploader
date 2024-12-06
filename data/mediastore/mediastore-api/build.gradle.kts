plugins {
    id("battman.jvm.library")
}

dependencies {
    api(projects.domain.models)

    api(libs.arrow.core)
    api(libs.kotlinx.coroutines.core.jvm)
}