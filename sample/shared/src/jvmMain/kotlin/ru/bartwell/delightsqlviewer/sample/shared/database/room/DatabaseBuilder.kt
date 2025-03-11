package ru.bartwell.delightsqlviewer.sample.shared.database.room

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseBuilder {
    actual fun createBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "sample_room.db")
        return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
    }
}
