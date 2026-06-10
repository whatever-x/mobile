import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.whatever.caramel.composeapp"

    defaultConfigs {
        buildConfigField(
            INT,
            "VERSION_CODE",
            libs.versions.version.code
                .get(),
        )
    }
}

kotlin {
    android {
        namespace = "com.whatever.caramel.composeapp"
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(project(":core:deeplink"))
            export(project(":core:firebase-messaging"))
            freeCompilerArgs += "-Xbinary=bundleId=com.whatever.caramel"
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Project
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(projects.core.remote)
            implementation(projects.core.inappReview)
            implementation(projects.core.analytics)
            implementation(projects.core.crashlytics)
            implementation(projects.core.viewmodel)
            api(projects.core.deeplink)
            api(projects.core.firebaseMessaging)
            implementation(projects.feature.profile.edit)
            implementation(projects.feature.profile.create)
            implementation(projects.feature.splash)
            implementation(projects.feature.setting)
            implementation(projects.feature.login)
            implementation(projects.feature.calendar)
            implementation(projects.feature.balancegame)
            implementation(projects.feature.couple.connect)
            implementation(projects.feature.couple.invite)
            implementation(projects.feature.couple.connecting)
            implementation(projects.feature.home)
            implementation(projects.feature.login)
            implementation(projects.feature.main)
            implementation(projects.feature.memo)
            implementation(projects.feature.content.create)
            implementation(projects.feature.content.edit)
            implementation(projects.feature.content.detail)

            // Library
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.androidx.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)

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
