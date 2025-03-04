package com.whatever.caramel.buildlogic.convention

import com.whatever.caramel.buildlogic.convention.extension.androidLibrary
import com.whatever.caramel.buildlogic.convention.extension.configureAndroid
import com.whatever.caramel.buildlogic.convention.extension.libs
import com.whatever.caramel.buildlogic.convention.extension.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

@Suppress("unused")
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply(libs.plugin("androidLibrary"))
            }

            androidLibrary {
                configureAndroid()
            }
        }
    }
}