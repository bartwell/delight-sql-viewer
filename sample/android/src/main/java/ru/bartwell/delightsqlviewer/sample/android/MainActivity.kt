package ru.bartwell.delightsqlviewer.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.sample.shared.App
import ru.bartwell.delightsqlviewer.sample.shared.DriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sqlDriver = DriverFactory(this).createDriver()
        DelightSqlViewer.init(object : AndroidEnvironmentProvider {
            override fun getSqlDriver() = sqlDriver
            override fun getContext() = this@MainActivity
        })
        setContent {
            App()
        }
    }
}
