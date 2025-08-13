plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
}

android.namespace = "com.whatever.caramel.core.ui"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(projects.core.util)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
