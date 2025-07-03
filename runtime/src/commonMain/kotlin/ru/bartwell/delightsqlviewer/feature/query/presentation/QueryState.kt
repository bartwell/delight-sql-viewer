package ru.bartwell.delightsqlviewer.feature.query.presentation

import ru.bartwell.delightsqlviewer.core.data.Row

internal data class QueryState(
    val query: String = "",
    val result: List<List<String?>> = emptyList(),
    val message: String = "",
    val isError: Boolean = false,
) {
    val rows: List<Row>
        get() = result.map { Row(id = 0L, data = it) }
}
