package com.whatever.caramel.buildlogic.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class GoogleServicesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }
        }
    }
}