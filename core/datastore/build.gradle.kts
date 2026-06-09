plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.datastore"
    }

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
