import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

/*
 The isRelease flag is created here to demonstrate one possible way to switch between
 release and debug builds. In release builds, we use the full-featured library, while in
 debug builds, we use a stub library.
*/
val isRelease = extra["isRelease"] as Boolean

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            if (isRelease) {
                export(projects.stub)
            } else {
                export(projects.library)
            }
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            if (isRelease) {
                api(projects.stub)
            } else {
                api(projects.library)
            }
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.sqldelight.driver.sqlite)
        }
    }
}

android {
    namespace = "ru.bartwell.delightsqlviewer.sample.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
}

sqldelight {
    databases {
        create("SampleDatabase") {
            packageName.set("ru.bartwell.delightsqlviewer.sample.shared")
        }
    }
}
