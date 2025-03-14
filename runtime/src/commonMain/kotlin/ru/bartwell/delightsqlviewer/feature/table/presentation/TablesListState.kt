package ru.bartwell.delightsqlviewer.feature.table.presentation

internal data class TablesListState(
    val tables: List<String> = emptyList(),
    val error: String? = null,
)
