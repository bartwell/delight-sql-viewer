package ru.bartwell.delightsqlviewer.sample.shared.database.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseBuilder(private val context: Context) {
    actual fun createBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("sample_room.db")
        return Room.databaseBuilder<AppDatabase>(context = appContext, name = dbFile.absolutePath)
    }
}
