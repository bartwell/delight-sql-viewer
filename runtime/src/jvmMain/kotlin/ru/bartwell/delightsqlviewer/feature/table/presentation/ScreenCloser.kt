package ru.bartwell.delightsqlviewer.feature.table.presentation

import androidx.compose.runtime.Composable
import ru.bartwell.delightsqlviewer.core.util.LocalComposeWindow

@Composable
internal actual fun screenCloser(): () -> Unit {
    val window = LocalComposeWindow.current
    return { window?.dispose() }
}
