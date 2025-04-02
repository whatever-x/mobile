import com.whatever.caramel.buildlogic.convention.extension.kotlin

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
}

android.namespace = "com.whatever.caramel.core.designsystem"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
    }
}