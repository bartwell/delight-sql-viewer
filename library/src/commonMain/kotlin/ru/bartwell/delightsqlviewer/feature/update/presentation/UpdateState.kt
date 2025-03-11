package ru.bartwell.delightsqlviewer.feature.update.presentation

import ru.bartwell.delightsqlviewer.core.data.Column

internal data class UpdateState(
    val table: String,
    val column: Column,
    val rowId: Long,
    val value: String? = "",
    val isNull: Boolean = false,
    val loadError: String? = null,
    val saveError: String? = null,
)
