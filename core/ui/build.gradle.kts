@file:OptIn(ExperimentalSpmForKmpFeature::class)

import io.github.frankois944.spmForKmp.swiftPackageConfig
import io.github.frankois944.spmForKmp.utils.ExperimentalSpmForKmpFeature

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    alias(libs.plugins.kmp.spm)
}

android.namespace = "com.whatever.caramel.core.ui"

android {
    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.swiftPackageConfig {
            minIos = "15.0"

            dependency {
                remotePackageVersion(
                    url = uri("https://github.com/googleads/swift-package-manager-google-mobile-ads.git"),
                    products = {
                        add("GoogleMobileAds", exportToKotlin = true)
                    },
                    version = "12.14.0",
                )
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.google.ad.mob)
        }
        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(projects.core.util)

            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}
