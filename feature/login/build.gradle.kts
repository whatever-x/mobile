import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.net.URI
import java.util.Locale

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
    id("caramel.kmp.test")
    alias(libs.plugins.kmp.spm)
}

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
                    cinterops.create("kakaoLoginBridge")
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.kakao.login)
        }
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.crashlytics)
            implementation(projects.core.viewmodel)
            implementation(projects.core.firebaseMessaging)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.jetbrains.compose.navigation)

            implementation(libs.bundles.moko)
        }
    }
}

swiftPackageConfig {
    create("kakaoLoginBridge") {
        customPackageSourcePath = "../../app-ios"
        minIos = "15.0"

        dependency {
            remotePackageVersion(
                url = URI("https://github.com/kakao/kakao-ios-sdk"),
                version = "2.24.4",
                products = { add("KakaoSDK") },
            )
        }
    }
}

android {
    namespace = "com.whatever.caramel.feature.login"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val kakaoNativeAppKey = gradleLocalProperties(rootDir, providers).getProperty("KAKAO_NATIVE_APP_KEY")
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey)

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey.replace("\"", "")
    }
}
