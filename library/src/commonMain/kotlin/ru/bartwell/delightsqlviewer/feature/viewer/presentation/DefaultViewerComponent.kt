package ru.bartwell.delightsqlviewer.feature.viewer.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.core.component.Resumable
import ru.bartwell.delightsqlviewer.core.extension.delete
import ru.bartwell.delightsqlviewer.core.extension.query
import ru.bartwell.delightsqlviewer.feature.viewer.data.Column
import ru.bartwell.delightsqlviewer.feature.viewer.data.ColumnsSqlMapper
import ru.bartwell.delightsqlviewer.feature.viewer.data.RowsSqlMapper

internal class DefaultViewerComponent(
    componentContext: ComponentContext,
    table: String,
    private val onFinished: () -> Unit,
    private val cellClick: (table: String, column: Column, rowId: Long) -> Unit,
    private val structureClick: (table: String) -> Unit,
    private val insertClick: (table: String, columns: List<Column>) -> Unit,
) : ViewerComponent, ComponentContext by componentContext, Resumable {

    private val _model = MutableValue(ViewerState(table = table))
    override val model: Value<ViewerState> = _model

    override fun onResume() {
        updateTable()
    }

    private fun updateTable() {
        loadColumns()
        loadRows()
    }

    private fun loadColumns() {
        val table = model.value.table
        DelightSqlViewer.getDriver()
            .query("PRAGMA table_info($table);", ColumnsSqlMapper())
            .onEach { columns ->
                _model.value = _model.value.copy(columns = columns + listOf(Column.ROW_ID_COLUMN))
            }
            .catch { _model.value = _model.value.copy(loadError = it.toString()) }
            .launchIn(coroutineScope())
    }

    private fun loadRows() {
        val table = model.value.table
        val columns = model.value.columns.joinToString(",") { it.name }
        DelightSqlViewer.getDriver()
            .query("SELECT $columns FROM $table;", RowsSqlMapper(model.value.columns))
            .onEach { _model.value = _model.value.copy(rows = it) }
            .catch { _model.value = _model.value.copy(loadError = it.toString()) }
            .launchIn(coroutineScope())
    }

    override fun onDeleteClick() {
        _model.value = model.value.copy(
            isDeleteMode = !model.value.isDeleteMode,
            selectedRows = if (model.value.isDeleteMode) emptyList() else model.value.selectedRows,
        )
    }

    override fun onRowSelected(rowId: Long, isSelected: Boolean) {
        val newSelected = if (isSelected) {
            model.value.selectedRows + rowId
        } else {
            model.value.selectedRows - rowId
        }
        _model.value = model.value.copy(selectedRows = newSelected)
    }

    override fun onBackPressed() = onFinished()

    override fun onCancelDeleteClick() {
        _model.value = model.value.copy(isDeleteMode = false)
    }

    override fun onConfirmDeleteClick() {
        DelightSqlViewer.getDriver()
            .delete(model.value.table, model.value.selectedRows)
            .onEach {
                updateTable()
                _model.value = model.value.copy(isDeleteMode = false)
            }
            .catch { _model.value = _model.value.copy(deleteError = it.toString()) }
            .launchIn(coroutineScope())
    }

    override fun onStructureClick() = structureClick(model.value.table)

    override fun onInsertClick() = insertClick(model.value.table, model.value.visibleColumns)

    override fun onCellClick(column: Column, rowId: Long) = cellClick(model.value.table, column, rowId)

    override fun onAlertDismiss() {
        _model.value = model.value.copy(deleteError = null)
    }
}
