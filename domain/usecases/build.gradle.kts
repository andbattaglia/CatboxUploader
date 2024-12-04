plugins {
    id("battman.jvm.library")
}

dependencies {
    api(projects.core.usecase.usecaseApi)
    api(projects.domain.models)
    api(libs.arrow.core)

    implementation(projects.data.countries.countriesApi)
    implementation(libs.javax.inject)
}
