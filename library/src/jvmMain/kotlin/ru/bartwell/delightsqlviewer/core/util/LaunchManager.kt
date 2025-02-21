package ru.bartwell.delightsqlviewer.core.util

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.awt.ComposeWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.bartwell.delightsqlviewer.App
import ru.bartwell.delightsqlviewer.DesktopEnvironmentProvider
import ru.bartwell.delightsqlviewer.EnvironmentProvider
import ru.bartwell.delightsqlviewer.core.component.DefaultRootComponent
import java.awt.Dimension

private const val WINDOW_WIDTH = 800
private const val WINDOW_HEIGHT = 600
private const val MIN_WINDOW_SIZE = 400

internal val LocalComposeWindow = staticCompositionLocalOf<ComposeWindow?> { null }

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object LaunchManager {
    actual fun launch(environmentProvider: EnvironmentProvider) {
        environmentProvider as DesktopEnvironmentProvider
        val lifecycle = LifecycleRegistry()
        val componentContext = DefaultComponentContext(lifecycle)
        val rootComponent = DefaultRootComponent(componentContext)

        val window = ComposeWindow().apply {
            title = "Viewer"
            size = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
            minimumSize = Dimension(MIN_WINDOW_SIZE, MIN_WINDOW_SIZE)
            preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
            setLocationRelativeTo(null)
            setContent {
                CompositionLocalProvider(LocalComposeWindow provides window) {
                    App(rootComponent)
                }
            }
        }
        window.pack()
        window.isVisible = true
    }
}
