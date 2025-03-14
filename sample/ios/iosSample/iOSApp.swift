import SwiftUI
import shared

class DatabaseInitializerImpl: DatabaseInitializer {
    
    init () {
        // Initialize SqlDelight for shortcut
        doInitSqlDelight()
    }

    func doInitSqlDelight() {
        let sqlDriver = DriverFactory().createDriver()
        let provider = SqlDelightEnvironmentProvider(driver: sqlDriver)
        DelightSqlViewer.shared.doInit(provider: provider, isShortcutEnabled: true)
    }

    func doInitRoom() {
        let databaseBuilder = DatabaseBuilder().createBuilder()
        let roomDatabase = AppDatabase.companion.create(builder: databaseBuilder)
        let provider = RoomEnvironmentProvider(driver: roomDatabase)
        DelightSqlViewer.shared.doInit(provider: provider, isShortcutEnabled: true)
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    let databaseInitializer: DatabaseInitializer = DatabaseInitializerImpl()
    var body: some Scene {
        WindowGroup {
            ContentView(databaseInitializer: databaseInitializer)
        }
    }
}
