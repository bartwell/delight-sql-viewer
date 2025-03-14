package ru.bartwell.delightsqlviewer.adapter.sqldelight

import app.cash.sqldelight.db.SqlDriver
import ru.bartwell.delightsqlviewer.IosEnvironmentProvider
import ru.bartwell.delightsqlviewer.core.DatabaseWrapper

public class SqlDelightEnvironmentProvider(driver: SqlDriver) : IosEnvironmentProvider<SqlDriver>(driver) {
    override fun getWrapper(): DatabaseWrapper = SqlDelightWrapper(driver)
}
