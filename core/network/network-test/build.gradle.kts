plugins {
    id("battman.jvm.library")
}

dependencies {
    api(projects.core.test.testJvm)

    implementation(libs.arrow.core.retrofit)
    implementation(libs.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.mockwebserver)
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
}
