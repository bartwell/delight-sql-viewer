package ru.bartwell.delightsqlviewer.feature.insert.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component
import ru.bartwell.delightsqlviewer.core.data.Column

internal interface InsertComponent : Component {
    val model: Value<InsertState>

    fun onBackPressed()
    fun onValueChange(column: Column, text: String)
    fun onValueTypeChange(column: Column, type: InsertValueType)
    fun onSaveClick()
    fun onAlertDismiss()
}
