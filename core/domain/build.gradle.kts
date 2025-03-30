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
        }
        // @RyuSw-cs 2025.03.30 모든 모듈에 테스트 코드를 개발할 경우 해당 dependencies를 build-logic에 적용
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))

            implementation(libs.kotlinx.coroutines.test)
        }
    }
}