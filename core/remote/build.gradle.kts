import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.kotlin.serialization")
}

android.namespace = "com.whatever.caramel.core.remote"

android {
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        val properties = Properties().apply { load(rootProject.file("local.properties").inputStream()) }
        val debugUrl = "CARAMEL_DEBUG_URL"
        val releaseUrl = "CARAMEL_RELEASE_URL"
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
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.bundles.ktor)
            implementation(libs.koin.core)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
