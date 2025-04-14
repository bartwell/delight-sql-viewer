import SwiftUI
import shared

enum DatabaseType: String, CaseIterable, Identifiable {
    case sqlDelight = "SqlDelight"
    case room = "Room"
    
    var id: String { self.rawValue }
}

struct ContentView: View {
    let databaseInitializer: DatabaseInitializer
    @State private var selectedDatabase: DatabaseType = .sqlDelight
    @State private var selectedTheme: AppTheme = .auto
    
    var body: some View {
        VStack(spacing: 20) {
            Picker("Select database", selection: $selectedDatabase) {
                ForEach(DatabaseType.allCases) { db in
                    Text(db.rawValue).tag(db)
                }
            }
            .pickerStyle(SegmentedPickerStyle())
            .padding()
            
            Picker("Select theme", selection: $selectedTheme) {
                ForEach(AppTheme.allCases) { theme in
                    Text(theme.rawValue).tag(theme)
                }
            }
            .pickerStyle(SegmentedPickerStyle())
            .padding()
            
            Button("Launch viewer") {
                launchViewer()
            }
            .buttonStyle(.borderedProminent)
            .padding()
        }
        .padding()
        .preferredColorScheme(colorScheme(for: selectedTheme))
    }
    
    private func colorScheme(for theme: AppTheme) -> ColorScheme? {
        switch theme {
        case .auto:
            return nil
        case .dark:
            return .dark
        case .light:
            return .light
        }
    }
    
    func launchViewer() {
        switch selectedDatabase {
        case .sqlDelight:
            databaseInitializer.doInitSqlDelight()
        case .room:
            databaseInitializer.doInitRoom()
        }
        DelightSqlViewer.shared.launch(theme: selectedTheme.toLibraryTheme())
    }
}
