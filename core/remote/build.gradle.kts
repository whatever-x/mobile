import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import java.util.Properties

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.kotlin.serialization")
    alias(libs.plugins.ksp)
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
        val sampleUrl = "CARAMEL_SAMPLE_URL"

        getByName("release") {
            isMinifyEnabled = false

            buildConfigField(
                "String",
                "BASE_URL",
                properties.getProperty(releaseUrl),
            )
            buildConfigField(
                "String",
                "SAMPLE_URL",
                properties.getProperty(sampleUrl),
            )
        }

        getByName("debug") {
            buildConfigField(
                "String",
                "BASE_URL",
                properties.getProperty(debugUrl),
            )
            buildConfigField(
                "String",
                "SAMPLE_URL",
                properties.getProperty(sampleUrl),
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

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
