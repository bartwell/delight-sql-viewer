package ru.bartwell.delightsqlviewer.sample.shared.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ru.bartwell.delightsqlviewer.sample.shared.SampleDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SampleDatabase.Schema, "sample.db")
    }
}
