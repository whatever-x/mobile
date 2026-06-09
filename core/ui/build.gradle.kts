@file:OptIn(ExperimentalSpmForKmpFeature::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import io.github.frankois944.spmForKmp.swiftPackageConfig
import io.github.frankois944.spmForKmp.utils.ExperimentalSpmForKmpFeature
import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    alias(libs.plugins.kmp.spm)
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.whatever.caramel.core.ui"

    val properties =
        Properties().apply {
            rootProject.file("local.properties").inputStream().use(::load)
        }

    defaultConfigs {
        buildConfigField(
            STRING,
            "ADMOB_TEST_BANNER_ID",
            properties.getProperty("ADMOB_TEST_BANNER_ID").removeSurrounding("\""),
        )
        buildConfigField(
            STRING,
            "ADMOB_HOME_BANNER_ID",
            properties.getProperty("ADMOB_HOME_BANNER_ID").removeSurrounding("\""),
        )
    }
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.ui"
    }

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
