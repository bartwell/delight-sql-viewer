import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    init() {
        let sqlDriver = DriverFactory().createDriver()
        let provider = IosEnvironmentProvider(sqlDriver: sqlDriver)
        DelightSqlViewer.shared.doInit(provider: provider, isShortcutEnabled: true)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
