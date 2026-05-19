plugins {
    id("caramel.kmp")
    id("caramel.kmp.ios")
    id("caramel.kmp.android")
}

android.namespace = "com.whatever.caramel.core.viewmodel"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.crashlytics)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel.savestate)
        }
    }
}
