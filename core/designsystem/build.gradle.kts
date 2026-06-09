plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.designsystem"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
