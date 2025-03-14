package ru.bartwell.delightsqlviewer.sample.shared.database.room

import androidx.room.RoomDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseBuilder {
    fun createBuilder(): RoomDatabase.Builder<AppDatabase>
}
