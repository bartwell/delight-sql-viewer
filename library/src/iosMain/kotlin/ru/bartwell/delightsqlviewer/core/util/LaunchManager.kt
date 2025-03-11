package ru.bartwell.delightsqlviewer.core.util

import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object LaunchManager {
    actual fun launch(environmentProvider: EnvironmentProvider<*>) = IosSceneController.present()
}
