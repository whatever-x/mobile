import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.kotlin.serialization")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmp.spm)
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

        fun getEnvOrProp(key: String): String = System.getenv(key) ?: properties.getProperty(key)

        getByName("release") {
            isMinifyEnabled = false

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${getEnvOrProp(key = releaseUrl)}\"",
            )
        }

        getByName("debug") {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${properties.getProperty(debugUrl)}\"",
            )
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.compilations {
            val main by getting {
                cinterops.create("keychainHelperBridge")
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        with(commonMain) {
            dependencies {
                implementation(libs.bundles.ktor)
                implementation(libs.koin.core)
                implementation(libs.koin.annotation)
            }
            configure {
                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
}


swiftPackageConfig {
    create("keychainHelperBridge") {
        customPackageSourcePath = "../../app-ios"
        minIos = "15.0"
    }
}