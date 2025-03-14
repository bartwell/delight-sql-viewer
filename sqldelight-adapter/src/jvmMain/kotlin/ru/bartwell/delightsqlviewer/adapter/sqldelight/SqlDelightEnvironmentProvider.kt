package ru.bartwell.delightsqlviewer.adapter.sqldelight

import app.cash.sqldelight.db.SqlDriver
import ru.bartwell.delightsqlviewer.core.DatabaseWrapper
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

public abstract class SqlDelightEnvironmentProvider : EnvironmentProvider<SqlDriver> {
    final override fun getWrapper(): DatabaseWrapper = SqlDelightWrapper(getDriver())
}
