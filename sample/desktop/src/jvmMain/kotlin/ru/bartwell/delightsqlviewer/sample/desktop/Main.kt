package ru.bartwell.delightsqlviewer.sample.desktop

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.DesktopEnvironmentProvider
import ru.bartwell.delightsqlviewer.sample.shared.App
import ru.bartwell.delightsqlviewer.sample.shared.DriverFactory

fun main(args: Array<String>) = application {
    val sqlDriver = DriverFactory().createDriver()
    DelightSqlViewer.init(object : DesktopEnvironmentProvider {
        override fun getSqlDriver() = sqlDriver
    })
    Window(
        title = "Wender",
        resizable = false,
        state = rememberWindowState(
            width = 600.dp,
            height = 600.dp,
            position = WindowPosition.Aligned(Alignment.Center),
        ),
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}
