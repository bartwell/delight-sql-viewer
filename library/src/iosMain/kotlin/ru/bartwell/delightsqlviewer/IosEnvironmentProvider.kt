package ru.bartwell.delightsqlviewer

import app.cash.sqldelight.db.SqlDriver

public class IosEnvironmentProvider(private val sqlDriver: SqlDriver) : EnvironmentProvider {
    public override fun getSqlDriver(): SqlDriver = sqlDriver
}
