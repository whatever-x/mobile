package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.android
import com.whatever.caramel.buildlogic.convention.extension.compose
import com.whatever.caramel.buildlogic.convention.extension.debugImplementation
import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.kspKmp
import com.whatever.caramel.buildlogic.convention.extension.libs
import com.whatever.caramel.buildlogic.convention.extension.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KmpComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("jetbrainsCompose"))
                apply(libs.plugin("composeCompiler"))
            }
            if (plugins.hasPlugin("androidLibrary")) {
                android {
                    buildFeatures.compose = true
                }
            }
            kotlin {
                with(sourceSets) {
                    getByName("commonMain") {
                        dependencies {
                            implementation(compose.dependencies.runtime)
                            implementation(compose.dependencies.foundation)
                            implementation(compose.dependencies.material3)
                            implementation(compose.dependencies.ui)
                            implementation(compose.dependencies.components.resources)
                            implementation(compose.dependencies.components.uiToolingPreview)
                        }
                    }

                    find { it.name == "androidMain" }?.apply {
                        dependencies {
                            implementation(compose.dependencies.preview)
                        }
                    }
                }
            }
        }
    }
}