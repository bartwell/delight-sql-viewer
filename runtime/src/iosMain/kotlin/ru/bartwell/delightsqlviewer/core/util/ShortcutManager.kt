package ru.bartwell.delightsqlviewer.core.util

import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationShortcutIcon
import platform.UIKit.UIApplicationShortcutIconType
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.shortcutItems
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object ShortcutManager {
    internal actual fun setup(environmentProvider: EnvironmentProvider<*>) {
        val shortcutItem = UIApplicationShortcutItem(
            type = id,
            localizedTitle = title,
            localizedSubtitle = subtitle,
            icon = UIApplicationShortcutIcon.iconWithType(
                UIApplicationShortcutIconType.UIApplicationShortcutIconTypeFavorite
            ),
            userInfo = null,
        )
        dispatch_async(dispatch_get_main_queue()) {
            UIApplication.sharedApplication.shortcutItems = listOf(shortcutItem)
        }
    }
}
