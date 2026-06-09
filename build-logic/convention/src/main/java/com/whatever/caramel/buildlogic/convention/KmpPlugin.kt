package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            kotlin {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }

                with(sourceSets) {
                    getByName("commonMain") {
                        dependencies {
                            implementation(libs.library("napier"))
                        }
                    }
                }
            }
        }
    }
}
