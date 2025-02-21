package ru.bartwell.delightsqlviewer.core.extension

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bartwell.delightsqlviewer.core.mapper.SqlMapper
import ru.bartwell.delightsqlviewer.feature.viewer.data.Column
import ru.bartwell.delightsqlviewer.feature.viewer.data.ColumnType

internal fun <T> SqlDriver?.query(sql: String, mapper: SqlMapper<T>): Flow<List<T>> = flow {
    requireNotNull(this@query)
    val result = executeQuery(
        identifier = null,
        sql = sql,
        parameters = 0,
        mapper = { cursor ->
            QueryResult.Value(
                buildList {
                    while (cursor.next().value) {
                        mapper.map(cursor)?.let { add(it) }
                    }
                }
            )
        }
    ).value
    emit(result)
}

internal fun <T> SqlDriver?.querySingle(sql: String, mapper: SqlMapper<T>): Flow<T?> = flow {
    requireNotNull(this@querySingle)
    val result = executeQuery(
        identifier = null,
        sql = sql,
        parameters = 0,
        mapper = { cursor ->
            QueryResult.Value(
                buildList {
                    val value = if (cursor.next().value) {
                        mapper.map(cursor)
                    } else {
                        null
                    }
                    add(value)
                }
            )
        }
    )
        .value
        .first()
    emit(result)
}

@OptIn(ExperimentalStdlibApi::class)
internal fun SqlDriver?.updateSingle(table: String, id: Long, column: Column, value: String?) = flow {
    requireNotNull(this@updateSingle)
    val columnName = column.name
    val sql = "UPDATE $table SET $columnName = ? WHERE rowid = ?"
    execute(
        identifier = null,
        sql = sql,
        parameters = 2,
        binders = {
            when (column.type) {
                ColumnType.INTEGER -> bindLong(index = 0, long = value?.toLong())
                ColumnType.TEXT -> bindString(index = 0, string = value)
                ColumnType.REAL -> bindDouble(index = 0, double = value?.toDouble())
                ColumnType.BLOB -> bindBytes(index = 0, bytes = value?.hexToByteArray())
            }
            bindLong(index = 1, long = id)
        }
    )
    emit(Unit)
}

@OptIn(ExperimentalStdlibApi::class)
internal fun SqlDriver?.insert(table: String, values: Map<Column, String?>) = flow {
    requireNotNull(this@insert)
    if (values.isEmpty()) {
        val sql = "INSERT INTO $table DEFAULT VALUES;"
        execute(identifier = null, sql = sql, parameters = 0)
    } else {
        val columnsPart = values.keys.joinToString(",") { it.name }
        val valuesPart = Array(values.size, { "?" }).joinToString(",")
        val sql = "INSERT INTO $table ($columnsPart) VALUES ($valuesPart)"
        execute(
            identifier = null,
            sql = sql,
            parameters = values.size,
            binders = {
                for (item in values.entries.withIndex()) {
                    val column = item.value.key
                    val value = item.value.value
                    when (column.type) {
                        ColumnType.INTEGER -> bindLong(index = item.index, long = value?.toLong())
                        ColumnType.TEXT -> bindString(index = item.index, string = value)
                        ColumnType.REAL -> bindDouble(index = item.index, double = value?.toDouble())
                        ColumnType.BLOB -> bindBytes(index = item.index, bytes = value?.hexToByteArray())
                    }
                }
            }
        )
    }
    emit(Unit)
}

internal fun SqlDriver?.delete(table: String, ids: List<Long>) = flow {
    requireNotNull(this@delete)
    if (ids.isNotEmpty()) {
        val whereClause = "rowid=" + ids.joinToString(" OR rowid=")
        val sql = "DELETE FROM $table WHERE $whereClause;"
        execute(identifier = null, sql = sql, parameters = 0)
    }
    emit(Unit)
}
