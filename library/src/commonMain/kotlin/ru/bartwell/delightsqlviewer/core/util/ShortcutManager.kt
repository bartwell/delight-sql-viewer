package ru.bartwell.delightsqlviewer.core.util

import ru.bartwell.delightsqlviewer.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object ShortcutManager {
    internal fun setup(environmentProvider: EnvironmentProvider)
}

internal val ShortcutManager.id: String
    get() = "delight_sql_viewer_shortcut"

internal val ShortcutManager.title: String
    get() = "SQL Viewer"

internal val ShortcutManager.subtitle: String
    get() = "Open SQL Viewer"
