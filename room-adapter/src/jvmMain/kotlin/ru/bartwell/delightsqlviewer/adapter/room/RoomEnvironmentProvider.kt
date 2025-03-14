package ru.bartwell.delightsqlviewer.adapter.room

import androidx.room.RoomDatabase
import ru.bartwell.delightsqlviewer.core.DatabaseWrapper
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

public abstract class RoomEnvironmentProvider : EnvironmentProvider<RoomDatabase> {
    final override fun getWrapper(): DatabaseWrapper = RoomWrapper(getDriver())
}
