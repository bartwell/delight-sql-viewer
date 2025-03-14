package ru.bartwell.delightsqlviewer.core.mapper

public interface SqlMapper<T> {
    public fun map(cursor: CursorWrapper<*>): T?
}
