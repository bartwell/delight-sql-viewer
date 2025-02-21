package ru.bartwell.delightsqlviewer.feature.update.data

import app.cash.sqldelight.db.SqlCursor
import ru.bartwell.delightsqlviewer.core.extension.getStringOrNull
import ru.bartwell.delightsqlviewer.core.mapper.SqlMapper
import ru.bartwell.delightsqlviewer.feature.viewer.data.Column

internal class SingleValueSqlMapper(private val column: Column) : SqlMapper<String> {
    override fun map(cursor: SqlCursor): String? {
        return cursor.getStringOrNull(column, 0)
    }
}
