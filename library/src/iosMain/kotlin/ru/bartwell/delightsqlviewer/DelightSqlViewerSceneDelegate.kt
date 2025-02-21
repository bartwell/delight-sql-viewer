package ru.bartwell.delightsqlviewer

import kotlinx.cinterop.BetaInteropApi
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.UIScene
import platform.UIKit.UISceneConnectionOptions
import platform.UIKit.UISceneSession
import platform.UIKit.UIWindowScene
import platform.UIKit.UIWindowSceneDelegateProtocol
import platform.darwin.NSObject
import ru.bartwell.delightsqlviewer.core.util.ShortcutManager
import ru.bartwell.delightsqlviewer.core.util.id

@OptIn(BetaInteropApi::class)
internal class DelightSqlViewerSceneDelegate @OverrideInit constructor() : NSObject(), UIWindowSceneDelegateProtocol {

    override fun scene(
        scene: UIScene,
        willConnectToSession: UISceneSession,
        options: UISceneConnectionOptions
    ) {
        handleAction(options.shortcutItem)
    }

    override fun windowScene(
        windowScene: UIWindowScene,
        performActionForShortcutItem: UIApplicationShortcutItem,
        completionHandler: (Boolean) -> Unit
    ) {
        val isHandled = handleAction(performActionForShortcutItem)
        completionHandler(isHandled)
    }

    private fun handleAction(shortcutItem: UIApplicationShortcutItem?): Boolean {
        return if (shortcutItem?.type == ShortcutManager.id) {
            DelightSqlViewer.launch()
            true
        } else {
            false
        }
    }
}
