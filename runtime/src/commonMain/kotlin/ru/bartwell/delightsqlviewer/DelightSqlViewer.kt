package ru.bartwell.delightsqlviewer

import ru.bartwell.delightsqlviewer.core.DatabaseWrapper
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider
import ru.bartwell.delightsqlviewer.core.data.Theme
import ru.bartwell.delightsqlviewer.core.util.LaunchManager
import ru.bartwell.delightsqlviewer.core.util.ShortcutManager
import ru.bartwell.delightsqlviewer.core.util.id

public object DelightSqlViewer {

    private var environmentProvider: EnvironmentProvider<*>? = null
    internal var theme: Theme = Theme.Auto

    public fun init(provider: EnvironmentProvider<*>) {
        init(provider, true)
    }

    public fun init(provider: EnvironmentProvider<*>, isShortcutEnabled: Boolean) {
        environmentProvider = provider
        if (isShortcutEnabled) {
            ShortcutManager.setup(provider)
        }
    }

    internal fun getDriver(): DatabaseWrapper {
        val driver = environmentProvider?.getWrapper()
        requireNotNull(driver) { "Driver is null. Did you call DelightSqlViewer.init()?" }
        return driver
    }

    public fun launch() {
        launch(theme = Theme.Auto)
    }

    public fun launch(theme: Theme) {
        this.theme = theme
        environmentProvider?.let { LaunchManager.launch(it) }
    }

    public fun getShortcutId(): String = ShortcutManager.id
}
