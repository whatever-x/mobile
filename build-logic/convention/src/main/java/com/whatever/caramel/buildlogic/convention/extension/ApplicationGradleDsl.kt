package com.whatever.caramel.buildlogic.convention.extension

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.androidApplication(action: ApplicationExtension.() -> Unit) {
    extensions.configure(action)
}
