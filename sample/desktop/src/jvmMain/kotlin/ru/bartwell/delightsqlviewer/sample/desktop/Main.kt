package ru.bartwell.delightsqlviewer.sample.desktop

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.RoomEnvironmentProvider
import ru.bartwell.delightsqlviewer.adapter.sqldelight.SqlDelightEnvironmentProvider
import ru.bartwell.delightsqlviewer.sample.shared.App
import ru.bartwell.delightsqlviewer.sample.shared.DatabaseInitializer
import ru.bartwell.delightsqlviewer.sample.shared.database.room.AppDatabase
import ru.bartwell.delightsqlviewer.sample.shared.database.room.DatabaseBuilder
import ru.bartwell.delightsqlviewer.sample.shared.database.sqldelight.DriverFactory

fun main() = application {
    val databaseInitializer = object : DatabaseInitializer {
        override fun initSqlDelight() {
            val sqlDelightDriver = DriverFactory().createDriver()
            DelightSqlViewer.init(object : SqlDelightEnvironmentProvider() {
                override fun getDriver() = sqlDelightDriver
            })
        }

        override fun initRoom() {
            val roomDatabase = AppDatabase.create(DatabaseBuilder().createBuilder())
            DelightSqlViewer.init(object : RoomEnvironmentProvider() {
                override fun getDriver() = roomDatabase
            })
        }
    }
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
        App(databaseInitializer = databaseInitializer)
    }
}
