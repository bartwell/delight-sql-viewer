package ru.bartwell.delightsqlviewer

import app.cash.sqldelight.db.SqlDriver
import ru.bartwell.delightsqlviewer.adapter.sqldelight.SqlDelightWrapper
import ru.bartwell.delightsqlviewer.core.DbWrapper

public abstract class SqlDelightEnvironmentProvider : AndroidEnvironmentProvider<SqlDriver> {
    final override fun getWrapper(): DbWrapper = SqlDelightWrapper(getDriver())
}
