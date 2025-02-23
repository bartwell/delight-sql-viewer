package ru.bartwell.delightsqlviewer.feature.table.presentation

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun screenCloser(): () -> Unit {
//    LocalActivity is available in androidx.activity:activity-compose:1.10.0
//    val activity = LocalActivity.current
    val activity = LocalContext.current.findActivity()
    return { activity?.finish() }
}

private fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
