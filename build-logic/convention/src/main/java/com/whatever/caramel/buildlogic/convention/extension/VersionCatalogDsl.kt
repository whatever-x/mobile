package com.whatever.caramel.buildlogic.convention.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(name: String): String = findVersion(name).get().requiredVersion

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency = findLibrary(name).get().get()

internal fun VersionCatalog.plugin(name: String): PluginDependency = findPlugin(name).get().get()

internal fun VersionCatalog.bundle(name: String): ExternalModuleDependencyBundle = findBundle(name).get().get()