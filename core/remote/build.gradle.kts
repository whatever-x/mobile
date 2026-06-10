import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.kotlin.serialization")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmp.spm)
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.whatever.caramel.core.remote"

    val properties =
        Properties().apply {
            rootProject.file("local.properties").inputStream().use(::load)
        }
    val requestedTasks =
        gradle.startParameter.taskNames
            .joinToString(separator = " ")
            .lowercase()
    val flavor =
        providers.gradleProperty("buildkonfig.flavor").orNull
            ?: when {
                "qa" in requestedTasks -> "qa"
                "release" in requestedTasks -> "release"
                else -> "debug"
            }

    fun configValue(key: String): String =
        System.getenv(key)
            ?: properties.getProperty(key)
            ?: error("Missing '$key' in local.properties or environment.")

    defaultConfigs {
        buildConfigField(
            STRING,
            "BASE_URL",
            when (flavor) {
                "release" -> configValue("CARAMEL_RELEASE_URL")
                "qa" -> configValue("CARAMEL_QA_URL")
                else -> configValue("CARAMEL_DEBUG_URL")
            }.removeSurrounding("\""),
        )
        buildConfigField(
            BOOLEAN,
            "DEBUG",
            (flavor != "release").toString(),
        )
    }
}

kotlin {
    android {
        namespace = "com.whatever.caramel.core.remote"
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
