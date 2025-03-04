import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
}

android.namespace = "com.whatever.caramel.core.data"

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
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
        }
    }
}