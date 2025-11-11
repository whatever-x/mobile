plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android {
    namespace = "com.whatever.caramel.core.inAppReview"
    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.play.review)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
