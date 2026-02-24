import Foundation

@MainActor
final class PairingViewModel: ObservableObject {
    @Published var pairingToken = ""
    @Published var selectedRole: FleetRole = .owner
    @Published var isLoading = false
    @Published var statusMessage = ""
    @Published var pairedVehicleId = ""

    private let apiClient: APIClient
    private let sessionStore: AuthSessionStore

    init(apiClient: APIClient, sessionStore: AuthSessionStore) {
        self.apiClient = apiClient
        self.sessionStore = sessionStore
    }

    func completePairing() async {
        statusMessage = ""
        guard let session = sessionStore.session else {
            statusMessage = "Sign in first"
            return
        }

        let cleanToken = extractPairingToken(from: pairingToken)
        guard !cleanToken.isEmpty else {
            statusMessage = "Enter a valid pairing token"
            return
        }

        isLoading = true
        defer { isLoading = false }

        do {
            let result = try await apiClient.completePairing(
                token: cleanToken,
                role: selectedRole,
                userToken: session.token
            )
            pairedVehicleId = result.vehicleId
            UserDefaults.standard.set(result.vehicleId, forKey: "active_vehicle_id")
            statusMessage = "Paired vehicle \(result.vehicleId) as \(result.role)"
        } catch {
            statusMessage = error.localizedDescription
        }
    }

    func setScannedPayload(_ payload: String) {
        let token = extractPairingToken(from: payload)
        pairingToken = token
    }

    private func extractPairingToken(from raw: String) -> String {
        let trimmed = raw.trimmingCharacters(in: .whitespacesAndNewlines)
        if trimmed.isEmpty { return "" }

        if let data = trimmed.data(using: .utf8),
           let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any],
           let token = json["pairing_token"] as? String,
           !token.isEmpty {
            return token
        }

        return trimmed
    }
}
