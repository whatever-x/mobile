import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty

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

    defaultConfigs { }

    /**
     * :core:remote:compileAndroidMain 같은 flavor가 없는 Android target 컴파일을 위한 기본값입니다.
     * 실제 앱 variant 빌드에서는 아래 flavor별 targetConfigs 값으로 대체됩니다.
     *
     * @author 함건형
     */
    targetConfigs {
        create("android") {
            buildConfigField(
                STRING,
                "CARAMEL_BASE_URL",
                rootProject
                    .getLocalProperty("CARAMEL_DEBUG_URL")
                    ?.removeSurrounding("\"")
                    ?: error("Missing 'CARAMEL_DEBUG_URL' in local.properties."),
            )
        }
    }

    mapOf(
        "dev" to "CARAMEL_DEBUG_URL",
        "qa" to "CARAMEL_DEBUG_URL",
        "prod" to "CARAMEL_RELEASE_URL",
    ).forEach { (flavor, urlKey) ->
        targetConfigs(flavor) {
            create("android") {
                buildConfigField(
                    STRING,
                    "CARAMEL_BASE_URL",
                    rootProject
                        .getLocalProperty(urlKey)
                        ?.removeSurrounding("\"")
                        ?: error("Missing '$urlKey' in local.properties."),
                )
            }
        }
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
