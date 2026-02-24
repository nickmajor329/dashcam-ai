import SwiftUI

@main
struct DashcamCompanionApp: App {
    @StateObject private var sessionStore = AuthSessionStore()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(sessionStore)
        }
    }
}
