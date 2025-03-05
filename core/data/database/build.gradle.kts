plugins {
    id("caramel.kmp")
    id("caramel.kmp.android")
    id("caramel.kmp.ios")
    id("caramel.room")
}

android.namespace = "com.whatever.caramel.core.data.database"

kotlin {
    room {
        schemaDirectory("$projectDir/schemas")
    }
}