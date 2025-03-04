package com.whatever.caramel.buildlogic.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
        }
    }
}
