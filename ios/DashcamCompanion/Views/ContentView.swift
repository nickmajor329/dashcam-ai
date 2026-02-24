import SwiftUI

struct ContentView: View {
    @EnvironmentObject var sessionStore: AuthSessionStore
    private let apiClient = APIClient()

    var body: some View {
        if sessionStore.session == nil {
            AuthView(viewModel: AuthViewModel(apiClient: apiClient, sessionStore: sessionStore))
        } else {
            TabView {
                PairingView(
                    sessionStore: sessionStore,
                    viewModel: PairingViewModel(apiClient: apiClient, sessionStore: sessionStore)
                )
                .tabItem {
                    Label("Pair", systemImage: "qrcode.viewfinder")
                }

                LiveView(
                    viewModel: LiveViewModel(apiClient: apiClient, sessionStore: sessionStore)
                )
                .tabItem {
                    Label("Live", systemImage: "video")
                }

                VStack {
                    Button("Sign Out") {
                        sessionStore.clear()
                    }
                    .buttonStyle(.borderedProminent)
                    .padding()
                }
                .tabItem {
                    Label("Account", systemImage: "person")
                }
            }
        }
    }
}
