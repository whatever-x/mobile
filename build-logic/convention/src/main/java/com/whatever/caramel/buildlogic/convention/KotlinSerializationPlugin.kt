package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.compose
import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            kotlin {
                with(sourceSets) {
                    getByName("commonMain") {
                        dependencies {
                            implementation(libs.library("kotlinx-serialization-json"))
                        }
                    }
                }
            }
        }
    }
}
