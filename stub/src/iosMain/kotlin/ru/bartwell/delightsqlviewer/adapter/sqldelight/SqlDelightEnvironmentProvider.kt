package ru.bartwell.delightsqlviewer.adapter.sqldelight

import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

@Suppress("UNUSED_PARAMETER")
public class SqlDelightEnvironmentProvider(driver: Any) : EnvironmentProvider<Any> {
    override fun getDriver(): Any = Unit
}
