package ru.bartwell.delightsqlviewer.adapter.room

import androidx.room.RoomDatabase
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.core.DatabaseWrapper

public abstract class RoomEnvironmentProvider : AndroidEnvironmentProvider<RoomDatabase> {
    final override fun getWrapper(): DatabaseWrapper = RoomWrapper(getDriver())
}
