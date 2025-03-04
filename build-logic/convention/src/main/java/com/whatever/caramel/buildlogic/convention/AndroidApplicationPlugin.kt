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
import org.jetbrains.kotlin.konan.properties.Properties

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            apply("com.android.application")

            androidApplication {
                android {
                    namespace?.let { this.namespace = it }
                    compileSdkVersion(libs.version("android-compileSdk"))
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
                        kotlinCompilerExtensionVersion = libs.version("compose-plugin")
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
                    buildTypes {
                        val properties =
                            Properties().apply { load(rootProject.file("local.properties").inputStream()) }
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
            }
        }
    }
}