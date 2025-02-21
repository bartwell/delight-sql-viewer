package ru.bartwell.delightsqlviewer.feature.viewer.data

import app.cash.sqldelight.db.SqlCursor
import ru.bartwell.delightsqlviewer.core.extension.getStringOrNull
import ru.bartwell.delightsqlviewer.core.mapper.SqlMapper

internal class RowsSqlMapper(private val columns: List<Column>) : SqlMapper<Row> {
    override fun map(cursor: SqlCursor): Row? {
        var id: Long? = null
        val data = mutableListOf<String?>()
        for (column in columns.withIndex()) {
            if (column.value.isRowId) {
                id = cursor.getLong(column.index)
            } else {
                data.add(cursor.getStringOrNull(column.value, column.index))
            }
        }

        return id?.let { Row(it, data) }
    }
}
