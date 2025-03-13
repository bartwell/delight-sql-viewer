package ru.bartwell.delightsqlviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import ru.bartwell.delightsqlviewer.core.component.DefaultRootComponent

public class DelightSqlViewerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = DefaultRootComponent(defaultComponentContext())
        setContent {
            App(rootComponent)
        }
    }
}
