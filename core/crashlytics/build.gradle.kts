import java.net.URI
import java.util.Locale

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    alias(libs.plugins.kmp.spm)
}

android {
    namespace = "com.whatever.caramel.core.crashlytics"
    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    val isWindow = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")

    if (!isWindow) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            iosTarget.compilations {
                val main by getting {
                    cinterops.create("firebaseCrashlyticsBridge")
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.koin.android)
            implementation(libs.firebase.crashlytics.ktx)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}

swiftPackageConfig {
    create("firebaseCrashlyticsBridge") {
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
