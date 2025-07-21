package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpIosPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = "App"
                        isStatic = true
                        export(project(":core:analytics"))
                        export(project(":core:crashlytics"))
                        export(project(":core:deeplink"))
                        export(project(":core:firebase-messaging"))
                        freeCompilerArgs += "-Xbinary=bundleId=com.whatever.caramel"
                    }
                }
            }
        }
    }
}