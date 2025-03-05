plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android.namespace = "com.whatever.caramel.core.data"


kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(projects.core.domain)

            implementation(projects.core.data.database)
            implementation(projects.core.data.datastore)
            implementation(projects.core.data.remote)
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}