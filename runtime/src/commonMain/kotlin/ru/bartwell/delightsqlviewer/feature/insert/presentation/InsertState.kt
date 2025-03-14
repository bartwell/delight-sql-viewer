package ru.bartwell.delightsqlviewer.feature.insert.presentation

import ru.bartwell.delightsqlviewer.core.data.Column

internal data class InsertState(
    val table: String,
    val columns: List<Column>,
    val values: Map<Column, String> = emptyMap(),
    val valueTypes: Map<Column, InsertValueType> = emptyMap(),
    val insertError: String? = null,
)

internal fun Map<Column, InsertValueType>.getOrDefault(column: Column) = this[column] ?: InsertValueType.entries[0]
