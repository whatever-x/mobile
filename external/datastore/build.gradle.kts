plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android.namespace = "com.whatever.caramel.external.datastore"

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.bundles.datastore)
            implementation(libs.koin.core)
        }
    }
}
