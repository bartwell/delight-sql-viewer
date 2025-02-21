package ru.bartwell.delightsqlviewer.core.util

import android.content.Intent
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.EnvironmentProvider
import ru.bartwell.delightsqlviewer.MainActivity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object LaunchManager {
    actual fun launch(environmentProvider: EnvironmentProvider) {
        val provider = environmentProvider as AndroidEnvironmentProvider
        val context = provider.getContext()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}
