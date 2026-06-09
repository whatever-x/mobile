import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.whatever.caramel.feature.setting"

    defaultConfigs {
        buildConfigField(
            STRING,
            "VERSION_NAME",
            libs.versions.version.name
                .get(),
        )
    }
}

kotlin {
    android {
        namespace = "com.whatever.caramel.feature.setting"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.crashlytics)
            implementation(projects.core.viewmodel)
            implementation(projects.core.util)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.androidx.compose.navigation)
            implementation(libs.kotlinx.date.time)
        }
    }
}
