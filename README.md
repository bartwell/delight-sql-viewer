# Delight SQL Viewer

**Delight SQL Viewer** is a Kotlin Multiplatform library for Android, iOS, and Desktop applications. It allows developers and testers to view, edit, add, and delete records in a [SQLDelight](https://github.com/cashapp/sqldelight)-based database directly within the application. This is particularly useful for debugging and QA purposes to quickly inspect and modify the app’s database state in real-time.

## Features

- **Multiplatform Support**: Works on Android, iOS, and Desktop targets.
- **Database Inspection**: View, edit, add, and delete records in a SQLDelight database.
- **App Shortcuts** (Android and iOS):
    - Automatically adds a shortcut menu entry on Android and iOS for quick access (can be disabled if needed).
- **Easy Integration**: Simply add the dependency to your shared module and initialize.
- **Configurable for Debug/Release**: Quickly disable or substitute the library for release builds.

## Table of Contents

1. [Installation](#installation)
2. [Initialization](#initialization)
    - [Android](#android)
    - [iOS](#ios)
    - [Desktop](#desktop)
3. [Launching the Viewer](#launching-the-viewer)
4. [Shortcuts](#shortcuts)
    - [Android Shortcut](#android-shortcut)
    - [iOS Shortcut](#ios-shortcut)
    - [Desktop](#desktop-shortcut)
5. [Excluding the Library in Release Builds](#excluding-the-library-in-release-builds)
    - [1. Use the Stub Library](#1-use-the-stub-library)
    - [2. Omit Initialization and Launch](#2-omit-initialization-and-launch)
6. [Contributing](#contributing)
7. [License](#license)

---

## Installation

Delight SQL Viewer is published to Maven Central. Add the dependency to your `shared` (common) module:

```kotlin
// In shared/build.gradle.kts

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("ru.bartwell.delightsqlviewer:library:1.0.0")
            }
        }
    }
}
```

For iOS, add the export statement in `framework {}` so it’s available in your iOS framework:

```kotlin
// In shared/build.gradle.kts

framework {
    export("ru.bartwell.delightsqlviewer:library:1.0.0")
}
```

## Initialization

Once the library is added, you need to initialize it in your platform-specific code with the appropriate environment provider. In each case, you must supply the `SqlDriver` you obtained from SQLDelight.

### Android

```kotlin
val sqlDriver: SqlDriver = /* SQLDelight driver */
DelightSqlViewer.init(
    object : AndroidEnvironmentProvider {
        override fun getSqlDriver() = sqlDriver
        override fun getContext() = this@MainActivity
    }
)
```

- The `AndroidEnvironmentProvider` requires the `SqlDriver` and an Android `Context`.
- By default, the library will add a shortcut (long-press on the app icon) to launch the viewer.

### iOS

```swift
let sqlDriver = /* SQLDelight driver */
let provider = IosEnvironmentProvider(sqlDriver: sqlDriver)
DelightSqlViewer.shared.doInit(provider: provider)
```

- Use `IosEnvironmentProvider` with your `SqlDriver`.
- The library will add a shortcut by default (long-press on the app icon), but you’ll need to handle the shortcut action in your `AppDelegate` or Scene delegate. See [iOS Shortcut](#ios-shortcut).

### Desktop

```kotlin
val sqlDriver: SqlDriver = /* SQLDelight driver */
DelightSqlViewer.init(
    object : DesktopEnvironmentProvider {
        override fun getSqlDriver() = sqlDriver
    }
)
```

- There is no built-in shortcut on Desktop. Simply call `DelightSqlViewer.launch()` from your own UI controls.

## Launching the Viewer

Once initialized, you can launch the viewer with a simple call:

### Android / Desktop

```kotlin
Button(onClick = { DelightSqlViewer.launch() }) {
    Text(text = "Launch viewer")
}
```

### iOS

```swift
Button("Launch viewer") {
    DelightSqlViewer.shared.launch()
}
```

## Shortcuts

### Android Shortcut

By default, **Delight SQL Viewer** adds a shortcut to your app’s launcher icon, which appears upon a long-press. You can disable this in the initialization step by passing a second parameter `isShortcutEnabled = false`:

```kotlin
DelightSqlViewer.init(
    object : AndroidEnvironmentProvider {
        override fun getSqlDriver() = sqlDriver
        override fun getContext() = this@MainActivity
    },
    isShortcutEnabled = false
)
```

### iOS Shortcut

Similarly, on iOS the library adds a long-press shortcut on the app icon. To handle shortcuts correctly, you need to configure your `AppDelegate` (or `UISceneDelegate`). For example:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        configurationForConnecting connectingSceneSession: UISceneSession,
        options: UIScene.ConnectionOptions
    ) -> UISceneConfiguration {
        return ShortcutActionHandler.shared.getConfiguration(session: connectingSceneSession)
    }
}
```

### Desktop Shortcut

Shortcuts are not supported on Desktop. You must manually trigger the viewer using `DelightSqlViewer.launch()` wherever appropriate in your app.

## Excluding the Library in Release Builds

Since this library is typically only needed for internal testing or development, you may want to exclude it from release builds. There are two main approaches:

### 1. Use the Stub Library

In your `shared/build.gradle.kts`, you can dynamically depend on the real library for debug builds and a stub library for release:

```kotlin
val isRelease = /* your logic to determine release vs. debug */

if (isRelease) {
    export("ru.bartwell.delightsqlviewer:stub:1.0.0")
} else {
    export("ru.bartwell.delightsqlviewer:library:1.0.0")
}

...

if (isRelease) {
    api("ru.bartwell.delightsqlviewer:stub:1.0.0")
} else {
    api("ru.bartwell.delightsqlviewer:library:1.0.0")
}
```

This way, the release build does not include the actual implementation, reducing app size.

### 2. Omit Initialization and Launch

Alternatively, you can simply never call `DelightSqlViewer.init(...)` or `DelightSqlViewer.launch()` in release builds. However, the stub approach is usually preferred to avoid unnecessary code bloat.

## Contributing

Contributions are welcome! Feel free to open an issue or create a pull request. Feedback and suggestions on how to improve the library are highly appreciated.

## License

```
Copyright 2023 [Your Name]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

```

Delight SQL Viewer is distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

---

**Happy debugging!** If you have any questions or need further assistance, feel free to open an issue.