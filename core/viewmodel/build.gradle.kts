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
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.savestate)
        }
    }
}
