package ru.bartwell.delightsqlviewer.core.mapper

import app.cash.sqldelight.db.SqlCursor

internal class StringSqlMapper : SqlMapper<String> {
    override fun map(cursor: SqlCursor): String? {
        return cursor.getString(0)
    }
}
