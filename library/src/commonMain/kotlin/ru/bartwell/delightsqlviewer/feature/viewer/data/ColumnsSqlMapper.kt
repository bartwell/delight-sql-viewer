package ru.bartwell.delightsqlviewer.feature.viewer.data

import app.cash.sqldelight.db.SqlCursor
import ru.bartwell.delightsqlviewer.core.mapper.SqlMapper

internal class ColumnsSqlMapper : SqlMapper<Column> {
    @Suppress("MagicNumber")
    override fun map(cursor: SqlCursor): Column? {
        val name = cursor.getString(1)
        val type = cursor.getString(2)
        return if (name != null && type != null) {
            Column(
                name = name,
                type = ColumnType.valueOf(type),
                isNotNullable = cursor.getBoolean(3) ?: false,
                defaultValue = cursor.getString(4),
            )
        } else {
            null
        }
    }
}
