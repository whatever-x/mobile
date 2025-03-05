enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }
}

rootProject.name = "caramel"
include(":app")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:designsystem")
include(":core:viewmodel")
include(":core:analytics")
include(":core:designsystem:ui")
include(":core:data:remote")
include(":core:data:database")
include(":core:data:datastore")
include(":feature")
include(":feature:splash")
include(":feature:login")
include(":feature:content")
include(":feature:couple")
include(":feature:main")
include(":feature:onboarding")
include(":feature:setting")
include(":feature:profile")
include(":feature:profile:edit")
include(":feature:profile:create")
include(":feature:calendar")
include(":feature:home")
include(":feature:memo")
include(":feature:couple:connect")
include(":feature:content")
include(":feature:couple:invite")
