plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android.namespace = "com.whatever.caramel.core.crashlytics"

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.koin.android)
            implementation(libs.firebase.crashlytics.ktx)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
