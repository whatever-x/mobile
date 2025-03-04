import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.whatever.caramel.buildlogic.convention"

repositories {
    google {
        content {
            includeGroupByRegex("com\\.android.*")
            includeGroupByRegex("com\\.google.*")
            includeGroupByRegex("androidx.*")
        }
    }
    mavenCentral()
    gradlePluginPortal()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.bundles.plugins)
}

gradlePlugin {
    plugins {
        register("kmp") {
            id = "caramel.kmp"
            implementationClass = "com.whatever.caramel.buildlogic.convention.KmpPlugin"
        }
        register("kmpAndroid") {
            id = "caramel.kmp.android"
            implementationClass = "com.whatever.caramel.buildlogic.convention.KmpAndroidPlugin"
        }
        register("kmpIos") {
            id = "caramel.kmp.ios"
            implementationClass = "com.whatever.caramel.buildlogic.convention.KmpIosPlugin"
        }
        register("kmpCompose") {
            id = "caramel.compose"
            implementationClass = "com.whatever.caramel.buildlogic.convention.KmpComposePlugin"
        }
        register("kotlinSerialization") {
            id = "caramel.kotlin.serialization"
            implementationClass =
                "com.whatever.caramel.buildlogic.convention.KotlinSerializationPlugin"
        }
        register("androidApplication") {
            id = "caramel.android.application"
            implementationClass =
                "com.whatever.caramel.buildlogic.convention.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "caramel.android.library"
            implementationClass =
                "com.whatever.caramel.buildlogic.convention.AndroidLibraryPlugin"
        }
        register("googleServices") {
            id = "caramel.google.services"
            implementationClass =
                "com.whatever.caramel.buildlogic.convention.GoogleServicesPlugin"
        }
        register("room") {
            id = "caramel.room"
            implementationClass = "com.whatever.caramel.buildlogic.convention.RoomPlugin"
        }
    }
}