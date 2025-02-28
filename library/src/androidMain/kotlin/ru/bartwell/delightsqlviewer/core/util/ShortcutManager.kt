package ru.bartwell.delightsqlviewer.core.util

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import ru.bartwell.delightsqlviewer.AndroidEnvironmentProvider
import ru.bartwell.delightsqlviewer.DelightSqlViewerActivity
import ru.bartwell.delightsqlviewer.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual object ShortcutManager {
    internal actual fun setup(environmentProvider: EnvironmentProvider) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val provider = environmentProvider as AndroidEnvironmentProvider
            val context = provider.getContext()
            context.getSystemService(ShortcutManager::class.java)?.let { shortcutManager ->
                val intent = Intent(context, DelightSqlViewerActivity::class.java)
                intent.setAction(Intent.ACTION_VIEW)
                val shortcut = ShortcutInfo.Builder(context, id)
                    .setShortLabel(title)
                    .setLongLabel(subtitle)
                    .setIcon(Icon.createWithResource(context, android.R.drawable.ic_menu_info_details))
                    .setIntent(intent)
                    .build()
                shortcutManager.dynamicShortcuts = listOf(shortcut)
            }
        }
    }
}
