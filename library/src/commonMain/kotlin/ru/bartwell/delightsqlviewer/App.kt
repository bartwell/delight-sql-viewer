package ru.bartwell.delightsqlviewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bartwell.delightsqlviewer.core.component.RootComponent
import ru.bartwell.delightsqlviewer.core.component.RootContent

@Composable
internal fun App(rootComponent: RootComponent) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .systemBarsPadding()
                .imePadding()
        ) {
            RootContent(
                modifier = Modifier.fillMaxSize(),
                component = rootComponent,
            )
        }
    }
}
