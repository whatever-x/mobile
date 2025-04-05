package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("dev.mokkery")
            }

            kotlin {
                with(sourceSets) {
                    getByName("commonTest") {
                        dependencies {
                            implementation(kotlin("test"))
                            implementation(kotlin("test-common"))
                            implementation(kotlin("test-annotations-common"))

                            implementation(libs.library("kotlinx-coroutines-test"))
                            implementation(libs.library("turbine"))
                        }
                    }
                }
            }
        }
    }
}