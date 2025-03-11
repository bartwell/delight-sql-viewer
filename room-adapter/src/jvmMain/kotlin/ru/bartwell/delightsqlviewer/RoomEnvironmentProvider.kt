package ru.bartwell.delightsqlviewer

import androidx.room.RoomDatabase
import ru.bartwell.delightsqlviewer.adapter.room.RoomWrapper
import ru.bartwell.delightsqlviewer.core.DbWrapper
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

public abstract class RoomEnvironmentProvider : EnvironmentProvider<RoomDatabase> {
    final override fun getWrapper(): DbWrapper = RoomWrapper(getDriver())
}
