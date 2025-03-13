package ru.bartwell.delightsqlviewer.feature.insert.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.core.data.Column

internal class DefaultInsertComponent(
    componentContext: ComponentContext,
    table: String,
    columns: List<Column>,
    private val onFinished: () -> Unit,
) : InsertComponent, ComponentContext by componentContext {

    private val _model = MutableValue(InsertState(table = table, columns = columns))
    override val model: Value<InsertState> = _model

    override fun onBackPressed() = onFinished()

    override fun onValueChange(column: Column, text: String) {
        val newMap = model.value.values.toMutableMap()
        newMap[column] = text
        _model.value = _model.value.copy(values = newMap)
    }

    override fun onValueTypeChange(column: Column, type: InsertValueType) {
        val newMap = model.value.valueTypes.toMutableMap()
        newMap[column] = type
        _model.value = _model.value.copy(valueTypes = newMap)
    }

    override fun onSaveClick() {
        val values = mutableMapOf<Column, String?>()
        for (column in model.value.columns) {
            values[column] = when (model.value.valueTypes[column]) {
                InsertValueType.DEFAULT, null -> continue
                InsertValueType.NULL -> null
                InsertValueType.VALUE -> model.value.values[column].orEmpty()
            }
        }
        DelightSqlViewer.getDriver()
            .insert(model.value.table, values)
            .onEach {
                onBackPressed()
            }
            .catch { _model.value = _model.value.copy(insertError = it.toString()) }
            .launchIn(coroutineScope())
    }

    override fun onAlertDismiss() {
        _model.value = _model.value.copy(insertError = null)
    }
}
