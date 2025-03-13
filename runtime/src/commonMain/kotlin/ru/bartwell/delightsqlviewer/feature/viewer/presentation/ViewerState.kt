package ru.bartwell.delightsqlviewer.feature.viewer.presentation

import ru.bartwell.delightsqlviewer.core.data.Column
import ru.bartwell.delightsqlviewer.core.data.Row

internal data class ViewerState(
    val table: String,
    val columns: List<Column> = emptyList(),
    val rows: List<Row> = emptyList(),
    val isDeleteMode: Boolean = false,
    val selectedRows: List<Long> = emptyList(),
    val deleteError: String? = null,
    val loadError: String? = null,
) {
    val visibleColumns: List<Column>
        get() = columns.filter { !it.isRowId }
}
