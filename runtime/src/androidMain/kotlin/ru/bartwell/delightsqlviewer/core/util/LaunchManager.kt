package ru.bartwell.delightsqlviewer.core.util

import android.content.Intent
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.DelightSqlViewerActivity
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object LaunchManager {
    actual fun launch(environmentProvider: EnvironmentProvider<*>) {
        val provider = environmentProvider as AndroidEnvironmentProvider
        val context = provider.getContext()
        val intent = Intent(context, DelightSqlViewerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
