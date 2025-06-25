package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.kotlin
import com.whatever.caramel.buildlogic.convention.extension.library
import com.whatever.caramel.buildlogic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("unused")
class KmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")

                tasks.withType(KotlinCompile::class.java) {
                    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            kotlin {
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
