package ru.bartwell.delightsqlviewer.adapter.room

import androidx.room.RoomDatabase
import ru.bartwell.delightsqlviewer.IosEnvironmentProvider
import ru.bartwell.delightsqlviewer.core.DbWrapper

public class RoomEnvironmentProvider(driver: RoomDatabase) : IosEnvironmentProvider<RoomDatabase>(driver) {
    override fun getWrapper(): DbWrapper = RoomWrapper(driver)
}
