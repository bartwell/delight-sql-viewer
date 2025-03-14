package ru.bartwell.delightsqlviewer.core.mapper

import ru.bartwell.delightsqlviewer.core.data.Column
import ru.bartwell.delightsqlviewer.core.data.ColumnType

public class ColumnsSqlMapper : SqlMapper<Column> {
    @Suppress("MagicNumber")
    override fun map(cursor: CursorWrapper<*>): Column? {
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
