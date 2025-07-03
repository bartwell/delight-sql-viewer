package ru.bartwell.delightsqlviewer.feature.query.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component

internal interface QueryComponent : Component {
    val model: Value<QueryState>

    fun onBackPressed()
    fun onQueryChange(text: String)
    fun onExecuteClick()
    fun onAlertDismiss()
}
