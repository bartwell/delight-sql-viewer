package ru.bartwell.delightsqlviewer.feature.viewer.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component
import ru.bartwell.delightsqlviewer.feature.viewer.data.Column

internal interface ViewerComponent : Component {
    val model: Value<ViewerState>

    fun onBackPressed()
    fun onStructureClick()
    fun onInsertClick()
    fun onCellClick(column: Column, rowId: Long)
    fun onDeleteClick()
    fun onRowSelected(rowId: Long, isSelected: Boolean)
    fun onCancelDeleteClick()
    fun onConfirmDeleteClick()
    fun onAlertDismiss()
}
