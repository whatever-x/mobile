plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
}

kotlin {
    android {
        namespace = "com.whatever.caramel.feature.balancegame"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.crashlytics)
            implementation(projects.core.viewmodel)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.androidx.compose.navigation)
            implementation(libs.kotlinx.date.time)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.moko.permission.compose)
            implementation(libs.moko.permission.storage)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
