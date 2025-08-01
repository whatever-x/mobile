plugins {
    id("caramel.kmp")
    id("caramel.kotlin.serialization")
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.date.time)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
