package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.kspKmp
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class RoomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            kotlin {
                with(sourceSets) {
                    getByName("commonMain").apply {
                        dependencies {
                            implementation(libs.library("androidx-room-runtime"))
                            implementation(libs.library("sqlite-bundled"))
                        }
                    }
                }
            }
            dependencies {
                kspKmp(libs.library("androidx-room-compiler"))
            }
        }
    }
}
