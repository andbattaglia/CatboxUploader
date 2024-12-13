plugins {
    id("battman.jvm.library")
}

dependencies {
    api(projects.domain.models)

    implementation(libs.arrow.core)
    implementation(libs.arrow.core.retrofit)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutines.core.jvm)
}