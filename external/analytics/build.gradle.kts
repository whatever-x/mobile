@file:OptIn(ExperimentalSpmForKmpFeature::class)

import io.github.frankois944.spmForKmp.swiftPackageConfig
import io.github.frankois944.spmForKmp.utils.ExperimentalSpmForKmpFeature
import java.net.URI

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    alias(libs.plugins.kmp.spm)
}

android.namespace = "com.whatever.caramel.external.analytics"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.swiftPackageConfig(cinteropName = "firebaseAnalyticsBridge") {
            customPackageSourcePath = "../../app-ios"
            minIos = "15.0"

            dependency {
                remotePackageVersion(
                    url = URI("https://github.com/firebase/firebase-ios-sdk"),
                    version = "11.9.0",
                    products = { add("FirebaseAnalytics") },
                )
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.analytics)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
