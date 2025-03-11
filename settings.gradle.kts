enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DelightSQLViewer"
include("android")
project(":android").projectDir = file("sample/android")
include("desktop")
project(":desktop").projectDir = file("sample/desktop")
include("shared")
project(":shared").projectDir = file("sample/shared")
include(":library")
include(":stub")
include(":core")
include(":sqldelight-adapter")
include(":room-adapter")
