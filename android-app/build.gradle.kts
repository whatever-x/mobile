import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    id("caramel.android.application")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
}

android {
    val admobAppIdKey = "ADMOB_APP_ID"
    val appsFlyerKey = "APPS_FLYER_KEY"
    val kakaoNativeAppKeyByFlavor =
        mapOf(
            "dev" to "KAKAO_NATIVE_APP_KEY_DEV",
            "qa" to "KAKAO_NATIVE_APP_KEY_QA",
            "prod" to "KAKAO_NATIVE_APP_KEY_PROD",
        )
    val storeFileKey = "STORE_FILE"
    val keyAliasKey = "KEY_ALIAS"
    val keyPasswordKey = "KEY_PASSWORD"
    val storePasswordKey = "STORE_PASSWORD"

    fun getRequiredLocalProperty(key: String): String =
        rootProject
            .getLocalProperty(key)
            ?.removeSurrounding("\"")
            ?: error("Missing '$key' in local.properties.")

    fun getKakaoNativeAppKey(flavor: String): String {
        val key = kakaoNativeAppKeyByFlavor.getValue(flavor)
        return getRequiredLocalProperty(key)
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

        manifestPlaceholders[admobAppIdKey] = getRequiredLocalProperty(admobAppIdKey)

        buildConfigField(
            "String",
            appsFlyerKey,
            "\"${
                getRequiredLocalProperty(appsFlyerKey)
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
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getKakaoNativeAppKey("dev")
            manifestPlaceholders["ONELINK_URI_SCHEME"] = "carameldev"
            manifestPlaceholders["ONELINK_PATH_PREFIX"] = "/lUSb"
            manifestPlaceholders["INVITE_ONELINK_URL"] = "https://caramel.onelink.me/lUSb/couple-invite"
        }

        create("qa") {
            dimension = "environment"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            resValue("string", "app_name", "Caramel-Qa")
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getKakaoNativeAppKey("qa")
            manifestPlaceholders["ONELINK_URI_SCHEME"] = "caramelqa"
            manifestPlaceholders["ONELINK_PATH_PREFIX"] = "/WjZR"
            manifestPlaceholders["INVITE_ONELINK_URL"] = "https://caramel.onelink.me/WjZR/couple-invite"
        }

        create("prod") {
            dimension = "environment"
            resValue("string", "app_name", "Caramel")
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getKakaoNativeAppKey("prod")
            manifestPlaceholders["ONELINK_URI_SCHEME"] = "caramel"
            manifestPlaceholders["ONELINK_PATH_PREFIX"] = "/7nAT"
            manifestPlaceholders["INVITE_ONELINK_URL"] = "https://caramel.onelink.me/7nAT/2l5wk4ab"
        }
    }

    signingConfigs {
        listOf(getByName("debug"), create("release")).forEach { signingConfig ->
            signingConfig.storeFile = rootProject.file(getRequiredLocalProperty(storeFileKey))
            signingConfig.keyAlias = getRequiredLocalProperty(keyAliasKey)
            signingConfig.keyPassword = getRequiredLocalProperty(keyPasswordKey)
            signingConfig.storePassword = getRequiredLocalProperty(storePasswordKey)
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
