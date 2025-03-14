package ru.bartwell.delightsqlviewer.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.adapter.room.RoomEnvironmentProvider
import ru.bartwell.delightsqlviewer.adapter.sqldelight.SqlDelightEnvironmentProvider
import ru.bartwell.delightsqlviewer.sample.shared.App
import ru.bartwell.delightsqlviewer.sample.shared.DatabaseInitializer
import ru.bartwell.delightsqlviewer.sample.shared.database.room.AppDatabase
import ru.bartwell.delightsqlviewer.sample.shared.database.room.DatabaseBuilder
import ru.bartwell.delightsqlviewer.sample.shared.database.sqldelight.DriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databaseInitializer = object : DatabaseInitializer {
            override fun initSqlDelight() {
                val sqlDelightDriver = DriverFactory(this@MainActivity).createDriver()
                DelightSqlViewer.init(object : SqlDelightEnvironmentProvider() {
                    override fun getDriver() = sqlDelightDriver
                    override fun getContext() = this@MainActivity
                })
            }

            override fun initRoom() {
                val roomDatabase = AppDatabase.create(DatabaseBuilder(this@MainActivity).createBuilder())
                DelightSqlViewer.init(object : RoomEnvironmentProvider() {
                    override fun getDriver() = roomDatabase
                    override fun getContext() = this@MainActivity
                })
            }
        }
        // Initialize SqlDelight for shortcut
        databaseInitializer.initSqlDelight()
        setContent {
            App(databaseInitializer = databaseInitializer)
        }
    }
}
