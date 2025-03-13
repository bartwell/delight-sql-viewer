package ru.bartwell.delightsqlviewer.feature.structure.presentation

import ru.bartwell.delightsqlviewer.core.data.Column

internal data class StructureState(
    val table: String,
    val columns: List<Column> = emptyList(),
    val error: String? = null,
)
