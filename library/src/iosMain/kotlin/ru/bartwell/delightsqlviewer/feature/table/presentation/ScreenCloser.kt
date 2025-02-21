package ru.bartwell.delightsqlviewer.feature.table.presentation

import androidx.compose.runtime.Composable
import ru.bartwell.delightsqlviewer.core.util.IosSceneController

@Composable
internal actual fun screenCloser(): () -> Unit {
    return { IosSceneController.dismiss() }
}
