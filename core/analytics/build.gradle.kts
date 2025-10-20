import java.net.URI
import java.util.Locale

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    alias(libs.plugins.kmp.spm)
}

android.namespace = "com.whatever.caramel.core.analytics"

kotlin {
    val isWindow = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")

    if (!isWindow) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.compilations {
                val main by getting {
                    cinterops.create("firebaseAnalyticsBridge")
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}

swiftPackageConfig {
    create("firebaseAnalyticsBridge") {
        customPackageSourcePath = "../../app-ios"
        minIos = "15.0"

        dependency {
            remotePackageVersion(
                url = URI("https://github.com/firebase/firebase-ios-sdk"),
                version = "11.9.0",
                products = { add("FirebaseAnalytics")}
            )
        }
    }
}