package ru.bartwell.delightsqlviewer.adapter.sqldelight

import app.cash.sqldelight.db.SqlDriver
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.core.DatabaseWrapper

public abstract class SqlDelightEnvironmentProvider : AndroidEnvironmentProvider<SqlDriver> {
    final override fun getWrapper(): DatabaseWrapper = SqlDelightWrapper(getDriver())
}
