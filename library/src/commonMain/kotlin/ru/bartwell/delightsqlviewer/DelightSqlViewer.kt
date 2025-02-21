package ru.bartwell.delightsqlviewer

import app.cash.sqldelight.db.SqlDriver
import ru.bartwell.delightsqlviewer.core.util.LaunchManager
import ru.bartwell.delightsqlviewer.core.util.ShortcutManager

public object DelightSqlViewer {

    private var environmentProvider: EnvironmentProvider? = null

    public fun init(provider: EnvironmentProvider) {
        init(provider, true)
    }

    public fun init(provider: EnvironmentProvider, isShortcutEnabled: Boolean) {
        environmentProvider = provider
        if (isShortcutEnabled) {
            ShortcutManager.setup(provider)
        }
    }

    internal fun getDriver(): SqlDriver? {
        return environmentProvider?.getSqlDriver()
    }

    public fun launch() {
        environmentProvider?.let { LaunchManager.launch(it) }
    }
}
