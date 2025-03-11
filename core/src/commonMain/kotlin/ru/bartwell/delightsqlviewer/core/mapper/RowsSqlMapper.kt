package ru.bartwell.delightsqlviewer.core.mapper

import ru.bartwell.delightsqlviewer.core.data.Column
import ru.bartwell.delightsqlviewer.core.data.Row

public class RowsSqlMapper(private val columns: List<Column>) : SqlMapper<Row> {
    public override fun map(cursor: CursorWrapper<*>): Row? {
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
