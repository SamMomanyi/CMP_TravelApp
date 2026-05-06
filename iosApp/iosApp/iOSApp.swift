import SwiftUI

@main
struct iOSApp: App {
    
    init()
     {
     
         if(ProcessInfo.processInfo.arguments.contains("CLEAR_DATA")){
             clearCacheData()
         }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
    
    
    private func clearCacheData() {
        guard let docs = FileManager.default
            .urls(for: .documentDirectory, in: .userDomainMask).first else { return }
        let storeFile = docs.appendingPathComponent("travenor_datastore.preferences_pb")
        try? FileManager.default.removeItem(at: storeFile)

    }
    
}
