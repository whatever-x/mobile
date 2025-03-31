plugins {
    id("caramel.kmp")
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
            implementation(project(":core:domain"))
            implementation(kotlin("test"))
        }
    }
}
