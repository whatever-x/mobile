import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("caramel.android.application")
    id("caramel.kmp")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
    id("caramel.google.services")
}

android.namespace = "com.whatever.caramel.app"

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)
        }
        commonMain.dependencies {
            // Project
            implementation(projects.core.designsystem)
            implementation(projects.core.designsystem.ui)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.data.datastore)
            implementation(projects.core.data.database)
            implementation(projects.core.data.remote)
            implementation(projects.core.analytics)
            implementation(projects.core.viewmodel)
            implementation(projects.feature.profile.edit)
            implementation(projects.feature.profile.create)
            implementation(projects.feature.splash)
            implementation(projects.feature.setting)
            implementation(projects.feature.onboarding)
            implementation(projects.feature.login)
            implementation(projects.feature.calendar)
            implementation(projects.feature.content)
            implementation(projects.feature.couple.connect)
            implementation(projects.feature.couple.invite)
            implementation(projects.feature.home)
            implementation(projects.feature.login)
            implementation(projects.feature.main)
            implementation(projects.feature.memo)

            // Library
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