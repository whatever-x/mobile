@file:OptIn(ExperimentalSpmForKmpFeature::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import io.github.frankois944.spmForKmp.swiftPackageConfig
import io.github.frankois944.spmForKmp.utils.ExperimentalSpmForKmpFeature
import java.net.URI

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    alias(libs.plugins.kmp.spm)
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.whatever.caramel.core.crashlytics"

    val requestedTasks =
        gradle.startParameter.taskNames
            .joinToString(separator = " ")
            .lowercase()
    val flavor =
        providers.gradleProperty("buildkonfig.flavor").orNull
            ?: if ("release" in requestedTasks && "qa" !in requestedTasks) "release" else "debug"

    defaultConfigs {
        buildConfigField(
            BOOLEAN,
            "DEBUG",
            (flavor != "release").toString(),
        )
    }
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.crashlytics"
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.swiftPackageConfig(cinteropName = "firebaseCrashlyticsBridge") {
            customPackageSourcePath = "../../app-ios"
            minIos = "15.0"

            dependency {
                remotePackageVersion(
                    url = URI("https://github.com/firebase/firebase-ios-sdk"),
                    version = "11.9.0",
                    products = { add("FirebaseCrashlytics") },
                )
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.crashlytics)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
