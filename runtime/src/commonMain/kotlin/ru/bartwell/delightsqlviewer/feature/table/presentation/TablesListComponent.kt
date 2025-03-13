package ru.bartwell.delightsqlviewer.feature.table.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component

internal interface TablesListComponent : Component {
    val model: Value<TablesListState>

    fun onListItemClicked(table: String)
}
