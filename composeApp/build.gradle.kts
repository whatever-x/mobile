import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.github.frankois944.spmForKmp.definition.SwiftDependency
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kmp.spm)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }

        iosTarget.compilations {
            val main by getting {
                cinterops.create("nativeIosShared")
            }
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kakao.login)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.napier)

            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.moko)
            implementation(libs.bundles.datastore)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

swiftPackageConfig {
    create("nativeIosShared") {
        customPackageSourcePath = "../iosApp"
        minIos = "15.0"

        dependency(
            SwiftDependency.Package.Remote.Version(
                url = URI("https://github.com/kakao/kakao-ios-sdk"),
                version = "2.23.0",
                products = { add("KakaoSDK") }
            ),
        )
    }
}

android {
    namespace = "com.whatever.caramel"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.whatever.caramel"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        val kakaoNativeAppKey = gradleLocalProperties(rootDir, providers).getProperty("KAKAO_NATIVE_APP_KEY")
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey)

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey.replace("\"","")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    signingConfigs {
        create("release") {
            Properties().apply {
                load(FileInputStream(rootProject.file("local.properties")))
                storeFile = rootProject.file(this["STORE_FILE"] as String)
                keyAlias = this["KEY_ALIAS"] as String
                keyPassword = this["KEY_PASSWORD"] as String
                storePassword = this["STORE_PASSWORD"] as String
            }
        }
    }

    buildTypes {
        val properties = Properties().apply { load(rootProject.file("local.properties").inputStream()) }
        val debugUrl = "CARAMEL_DEBUG_URL"
        val releaseUrl = "CARAMEL_RELEASE_URL"

        release {
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("release") {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "BASE_URL",
                properties.getProperty(releaseUrl)
            )
        }

        getByName("debug") {
            buildConfigField(
                "String",
                "BASE_URL",
                properties.getProperty(debugUrl)
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    ksp(libs.androidx.room.compiler)
}