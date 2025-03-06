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