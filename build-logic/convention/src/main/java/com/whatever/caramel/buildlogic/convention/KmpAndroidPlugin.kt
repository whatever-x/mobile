package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.android
import com.whatever.caramel.buildlogic.convention.extension.configureAndroid
import com.whatever.caramel.buildlogic.convention.extension.debugImplementation
import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libraryAndroidOptions
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

@Suppress("unused")
class KmpAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }
            kotlin {
                androidTarget {
                    compilations.all {
                        libraryAndroidOptions {
                            compileTaskProvider.configure {
                                compilerOptions {
                                    jvmTarget.set(JvmTarget.JVM_17)
                                }
                            }
                        }
                    }
                }
            }
            android {
                configureAndroid()
                dependencies {
                    debugImplementation(libs.library("androidx-compose-ui-tooling"))
                }
            }
        }
    }
}
