package ru.bartwell.delightsqlviewer.core.extension

import app.cash.sqldelight.db.SqlCursor
import ru.bartwell.delightsqlviewer.feature.viewer.data.Column
import ru.bartwell.delightsqlviewer.feature.viewer.data.ColumnType

@OptIn(ExperimentalStdlibApi::class)
internal fun SqlCursor.getStringOrNull(column: Column, index: Int) = when (column.type) {
    ColumnType.INTEGER -> getLong(index)?.toString()
    ColumnType.TEXT -> getString(index)
    ColumnType.REAL -> getDouble(index)?.toString()
    ColumnType.BLOB -> getBytes(index)?.toHexString()
}
