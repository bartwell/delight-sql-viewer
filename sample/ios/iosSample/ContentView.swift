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
    
    var body: some View {
        VStack(spacing: 20) {
            Picker("Select database", selection: $selectedDatabase) {
                ForEach(DatabaseType.allCases) { db in
                    Text(db.rawValue).tag(db)
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
    }
    
    func launchViewer() {
        switch selectedDatabase {
        case .sqlDelight:
            databaseInitializer.doInitSqlDelight()
        case .room:
            databaseInitializer.doInitRoom()
        }
        DelightSqlViewer.shared.launch()
    }
}

