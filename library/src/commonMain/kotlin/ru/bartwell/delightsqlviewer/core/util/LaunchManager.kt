package ru.bartwell.delightsqlviewer.core.util

import ru.bartwell.delightsqlviewer.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object LaunchManager {
    fun launch(environmentProvider: EnvironmentProvider)
}
