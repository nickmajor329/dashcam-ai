import Foundation

@MainActor
final class LiveViewModel: ObservableObject {
    @Published var vehicleId: String = UserDefaults.standard.string(forKey: "active_vehicle_id") ?? ""
    @Published var bitrateKbps: Int = 1200
    @Published var statusMessage: String = ""
    @Published var viewerURL: URL?
    @Published var nativeViewerSession: NativeViewerSession?
    @Published var isLoading: Bool = false

    private let apiClient: APIClient
    private let sessionStore: AuthSessionStore

    init(apiClient: APIClient, sessionStore: AuthSessionStore) {
        self.apiClient = apiClient
        self.sessionStore = sessionStore
    }

    func openLive() async {
        statusMessage = ""
        viewerURL = nil
        nativeViewerSession = nil

        guard let session = sessionStore.session else {
            statusMessage = "Sign in first"
            return
        }

        let cleanVehicle = vehicleId.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !cleanVehicle.isEmpty else {
            statusMessage = "Enter vehicle ID"
            return
        }

        isLoading = true
        defer { isLoading = false }

        do {
            let response = try await apiClient.requestNativeLiveSession(
                vehicleId: cleanVehicle,
                ownerId: session.userId,
                role: "viewer",
                bitrateKbps: bitrateKbps,
                userToken: session.token
            )

            if response.supported,
               let wsUrl = response.wsURL,
               let accessToken = response.accessToken {
                statusMessage = "Opening low-latency live view"
                nativeViewerSession = NativeViewerSession(wsURL: wsUrl, accessToken: accessToken)
                viewerURL = buildLiveKitViewerURL(wsURL: wsUrl, token: accessToken)
                return
            }

            if let fallback = response.fallbackLiveURL, let url = URL(string: fallback) {
                statusMessage = response.reason ?? "Using fallback live view"
                viewerURL = url
                return
            }

            statusMessage = response.reason ?? "Live session unavailable"
        } catch {
            statusMessage = error.localizedDescription
        }
    }

    private func buildLiveKitViewerURL(wsURL: String, token: String) -> URL? {
        guard let base = URL(string: AppConfig.backendBaseURL) else { return nil }
        guard var components = URLComponents(
            url: base.appendingPathComponent("live/viewer"),
            resolvingAgainstBaseURL: false
        ) else {
            return nil
        }
        components.queryItems = [
            URLQueryItem(name: "ws_url", value: wsURL),
            URLQueryItem(name: "token", value: token)
        ]
        return components.url
    }
}
