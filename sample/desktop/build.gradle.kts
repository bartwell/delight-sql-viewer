plugins {
    kotlin("multiplatform")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.shared)
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "ru.bartwell.delightsqlviewer.sample.desktop.MainKt"
//        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Pkg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
//            modules("jdk.crypto.ec", "java.sql")
//            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
//            packageName = "Wender"
//            packageVersion = version as String
//            copyright = "Copyright © 2009 by BArtWell"
//            vendor = "BArtWell"
//            description = "Cross-platform application to transfer files and directories between devices using WiFi"
//            windows {
//                iconFile.set(file("$rootDir/desktop/launcher.ico"))
//                menuGroup = "Wender"
//                upgradeUuid = "db0ca164-432f-49ed-9f53-490f37f49fec"
//                shortcut = true
//            }
//            macOS {
//                packageVersion = version as String
//                packageBuildVersion = versionCode.toString()
//                bundleID = "ru.kolif.wender"
//                minimumSystemVersion = "12.0"
//                appCategory = "public.app-category.utilities"
//                appStore = true
//                entitlementsFile.set(project.file("entitlements.plist"))
//                runtimeEntitlementsFile.set(project.file("runtime-entitlements.plist"))
//                provisioningProfile.set(project.file("embedded.provisionprofile"))
//                runtimeProvisioningProfile.set(project.file("runtime.provisionprofile"))
//                infoPlist {
//                    extraKeysRawXml = """
//            <key>ITSAppUsesNonExemptEncryption</key>
//            <string>false</string>
//        """.trimIndent()
//                    /*
//                    <key>NSDownloadsFolderUsageDescription</key>
//            <string>To send files from download directory</string>
//            <key>NSDesktopFolderUsageDescription</key>
//            <string>To send files from desktop</string>
//            <key>NSDocumentsFolderUsageDescription</key>
//            <string>To send files from documents directory</string>
//            <key>NSRemovableVolumesUsageDescription</key>
//            <string>To send files from removable volumes</string>
//            */
//                }
//                signing {
//                    sign.set(true)
//                    identity.set("Artem Bazhanov")
////                    keychain.set("/Users/bartwell/Library/Keychains/login.keychain-db")
//                }
//                // notarization {}
//                iconFile.set(file("$rootDir/desktop/AppIcon.icns"))
//                packageVersion = version as String
//                copyright = "Copyright © 2009 by BArtWell"
//                vendor = "BArtWell"
//
//                description = "Cross-platform application to transfer files and directories between devices using WiFi"
//            }
//        }
    }
}
