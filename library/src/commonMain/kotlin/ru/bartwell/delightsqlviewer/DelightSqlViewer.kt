package ru.bartwell.delightsqlviewer

import ru.bartwell.delightsqlviewer.core.DbWrapper
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider
import ru.bartwell.delightsqlviewer.core.util.LaunchManager
import ru.bartwell.delightsqlviewer.core.util.ShortcutManager
import ru.bartwell.delightsqlviewer.core.util.id

public object DelightSqlViewer {

    private var environmentProvider: EnvironmentProvider<*>? = null

    public fun init(provider: EnvironmentProvider<*>) {
        init(provider, true)
    }

    public fun init(provider: EnvironmentProvider<*>, isShortcutEnabled: Boolean) {
        environmentProvider = provider
        if (isShortcutEnabled) {
            ShortcutManager.setup(provider)
        }
    }

    internal fun getDriver(): DbWrapper {
        val driver = environmentProvider?.getWrapper()
        requireNotNull(driver) { "Driver is null. Did you call DelightSqlViewer.init()?" }
        return driver
    }

    public fun launch() {
        environmentProvider?.let { LaunchManager.launch(it) }
    }

    public fun getShortcutId(): String = ShortcutManager.id
}
