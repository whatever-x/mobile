import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("caramel.android.application")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
}

android {
    val localProperties = gradleLocalProperties(rootDir, providers)
    val admobAppIdKey = "ADMOB_APP_ID"
    val appsFlyerKey = "APPS_FLYER_KEY"
    val kakaoNativeAppKey = "KAKAO_NATIVE_APP_KEY"
    val storeFileKey = "STORE_FILE"
    val keyAliasKey = "KEY_ALIAS"
    val keyPasswordKey = "KEY_PASSWORD"
    val storePasswordKey = "STORE_PASSWORD"
    val requiredProperties =
        listOf(
            admobAppIdKey,
            appsFlyerKey,
            kakaoNativeAppKey,
            storeFileKey,
            keyAliasKey,
            keyPasswordKey,
            storePasswordKey,
        ).associateWith { key ->
            localProperties.getProperty(key) ?: error("Missing '$key' in local.properties.")
        }

    namespace = "com.whatever.caramel"

    defaultConfig {
        applicationId = "com.whatever.caramel"
        versionCode =
            libs.versions.version.code
                .get()
                .toInt()
        versionName =
            libs.versions.version.name
                .get()

        manifestPlaceholders[admobAppIdKey] =
            requiredProperties.getValue(admobAppIdKey).removeSurrounding("\"")
        manifestPlaceholders[kakaoNativeAppKey] =
            requiredProperties.getValue(kakaoNativeAppKey).removeSurrounding("\"")

        buildConfigField(
            "String",
            appsFlyerKey,
            "\"${
                requiredProperties.getValue(appsFlyerKey)
                    .removeSurrounding("\"")
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
            }\"",
        )
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "Caramel-Dev")
        }

        create("qa") {
            dimension = "environment"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            resValue("string", "app_name", "Caramel-Qa")
        }

        create("prod") {
            dimension = "environment"
            resValue("string", "app_name", "Caramel")
        }
    }

    signingConfigs {
        listOf(getByName("debug"), create("release")).forEach { signingConfig ->
            signingConfig.storeFile = rootProject.file(requiredProperties.getValue(storeFileKey))
            signingConfig.keyAlias = requiredProperties.getValue(keyAliasKey)
            signingConfig.keyPassword = requiredProperties.getValue(keyPasswordKey)
            signingConfig.storePassword = requiredProperties.getValue(storePasswordKey)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(projects.core.deeplink)
    implementation(projects.core.firebaseMessaging)
    implementation(projects.core.inappReview)

    implementation(libs.androidx.activity.compose)
    implementation(libs.jetbrains.androidx.compose.navigation)
    implementation(libs.koin.android)
    implementation(project.dependencies.platform(libs.firebase.bom.android))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.apps.flyer)
    implementation(libs.google.ad.mob)
    implementation(libs.napier)
}
