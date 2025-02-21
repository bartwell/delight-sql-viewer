package ru.bartwell.delightsqlviewer.core.mapper

import app.cash.sqldelight.db.SqlCursor

internal interface SqlMapper<T> {
    fun map(cursor: SqlCursor): T?
}
