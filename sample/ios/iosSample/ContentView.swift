import SwiftUI
import shared

struct ContentView: View {
    
    var body: some View {
        Button("Launch viewer") {
            DelightSqlViewer.shared.launch()
        }
        .buttonStyle(.borderedProminent)
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
