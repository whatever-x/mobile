plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
}

android.namespace = "com.whatever.caramel.feature.main"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.calendar)
            implementation(projects.feature.home)
            implementation(projects.feature.memo)
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.analytics)
            implementation(projects.core.viewmodel)
            implementation(projects.core.firebaseMessaging)

            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}
