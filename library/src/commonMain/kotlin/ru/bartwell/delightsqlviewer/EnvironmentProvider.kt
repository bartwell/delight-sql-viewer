package ru.bartwell.delightsqlviewer

import app.cash.sqldelight.db.SqlDriver

public interface EnvironmentProvider {
    public fun getSqlDriver(): SqlDriver
}
