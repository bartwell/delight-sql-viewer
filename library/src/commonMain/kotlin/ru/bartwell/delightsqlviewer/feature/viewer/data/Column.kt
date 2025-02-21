package ru.bartwell.delightsqlviewer.feature.viewer.data

import kotlinx.serialization.Serializable

private const val ROW_ID_COLUMN_NAME = "rowid"

@Serializable
internal data class Column(
    val name: String,
    val type: ColumnType,
    val isNotNullable: Boolean,
    val defaultValue: String?,
) {
    val isRowId: Boolean
        get() = name == ROW_ID_COLUMN_NAME

    companion object {
        val ROW_ID_COLUMN = Column(
            name = ROW_ID_COLUMN_NAME,
            type = ColumnType.INTEGER,
            isNotNullable = true,
            defaultValue = "",
        )
    }
}
