package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.android
import com.whatever.caramel.buildlogic.convention.extension.androidApplication
import com.whatever.caramel.buildlogic.convention.extension.libs
import com.whatever.caramel.buildlogic.convention.extension.plugin
import com.whatever.caramel.buildlogic.convention.extension.version
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
            }

            androidApplication {
                android {
                    namespace?.let { this.namespace = it }
                    compileSdkVersion(libs.version("android-compileSdk").toInt())
                    defaultConfig {
                        applicationId = "com.whatever.caramel"
                        minSdk = libs.version("android-minSdk").toInt()
                        targetSdk = libs.version("android-targetSdk").toInt()
                        versionCode = libs.version("version-code").toInt()
                        versionName = libs.version("version-name")
                    }
                    buildFeatures {
                        compose = true
                        buildConfig = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion = libs.version("compose")
                    }
                    packaging {
                        resources {
                            excludes += "/META-INF/{AL2.0,LGPL2.1}"
                        }
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                }
            }
        }
    }
}