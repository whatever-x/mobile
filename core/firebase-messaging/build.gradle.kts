import java.net.URI
import java.util.Locale

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    alias(libs.plugins.kmp.spm)
}

android.namespace = "com.whatever.caramel.core.firebaseMessaging"

kotlin {
    val isWindow = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")

    if (!isWindow) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            iosTarget.compilations {
                val main by getting {
                    cinterops.create("firebaseMessagingBridge")
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)

            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.fcm)
        }
        commonMain.dependencies {
            implementation(projects.core.remote)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

swiftPackageConfig {
    create("firebaseMessagingBridge") {
        customPackageSourcePath = "../../app-ios"
        minIos = "15.0"

        dependency {
            remotePackageVersion(
                url = URI("https://github.com/firebase/firebase-ios-sdk"),
                version = "11.9.0",
                products = { add("FirebaseMessaging") },
            )
        }
    }
}
