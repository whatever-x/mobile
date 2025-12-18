plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android.namespace = "com.whatever.caramel.core.data"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.datasource.local)
            implementation(projects.core.datasource.remote)

            implementation(projects.core.util)

            implementation(libs.koin.core)

            implementation(libs.bundles.ktor)
            implementation(libs.kotlinx.date.time)
        }
    }
}
