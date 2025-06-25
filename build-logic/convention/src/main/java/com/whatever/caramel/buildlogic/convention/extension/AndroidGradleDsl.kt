package com.whatever.caramel.buildlogic.convention.extension


import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.kotlinAndroidOptions(configure: KotlinAndroidProjectExtension.() -> Unit) {
    extensions.configure(configure)
}

fun Project.libraryAndroidOptions(configure: LibraryAndroidComponentsExtension.() -> Unit) {
    extensions.configure(configure)
}

fun Project.configureAndroid() {
    android {
        namespace?.let {
            this.namespace = it
        }
        compileSdkVersion(libs.version("android-compileSdk").toInt())
        defaultConfig {
            minSdk = libs.version("android-minSdk").toInt()
            targetSdk = libs.version("android-targetSdk").toInt()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
