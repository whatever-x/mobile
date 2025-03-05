plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
}

android.namespace = "com.whatever.caramel.feature.splash"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.designsystem.ui)
            implementation(projects.core.analytics)
            implementation(projects.core.viewmodel)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}