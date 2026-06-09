plugins {
    id("caramel.kmp")
    id("caramel.kmp.ios")
    id("caramel.kmp.android")
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.viewmodel"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.crashlytics)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel.savestate)
        }
    }
}
