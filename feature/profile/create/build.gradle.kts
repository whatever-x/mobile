plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.kmp.test")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
}

android.namespace = "com.whatever.caramel.feature.profile.create"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.analytics)
            implementation(projects.core.viewmodel)
            implementation(projects.core.util)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.compose.ui.backhandler)
            implementation(libs.kotlinx.date.time)

            implementation(libs.bundles.moko)
        }
    }
}
