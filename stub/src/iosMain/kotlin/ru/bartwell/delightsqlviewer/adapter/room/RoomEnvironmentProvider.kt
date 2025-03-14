package ru.bartwell.delightsqlviewer.adapter.room

import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

@Suppress("UNUSED_PARAMETER")
public class RoomEnvironmentProvider(driver: Any) : EnvironmentProvider<Any> {
    override fun getDriver(): Any = Unit
}
