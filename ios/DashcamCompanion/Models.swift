import Foundation

struct AuthSession: Codable {
    let userId: String
    let email: String
    let token: String
}

struct AuthUser: Codable {
    let id: String
    let email: String
}

struct AuthResponse: Codable {
    let ok: Bool
    let token: String
    let user: AuthUser
}

struct PairingCompleteResponse: Codable {
    let vehicleId: String
    let ownerId: String
    let role: String
    let paired: Bool

    enum CodingKeys: String, CodingKey {
        case vehicleId = "vehicle_id"
        case ownerId = "owner_id"
        case role
        case paired
    }
}

struct NativeLiveSessionResponse: Codable {
    let supported: Bool
    let provider: String?
    let wsURL: String?
    let roomName: String?
    let participantIdentity: String?
    let role: String?
    let accessToken: String?
    let fallbackLiveURL: String?
    let reason: String?
    let expiresInSeconds: Int?

    enum CodingKeys: String, CodingKey {
        case supported
        case provider
        case wsURL = "ws_url"
        case roomName = "room_name"
        case participantIdentity = "participant_identity"
        case role
        case accessToken = "access_token"
        case fallbackLiveURL = "fallback_live_url"
        case reason
        case expiresInSeconds = "expires_in_seconds"
    }
}

enum FleetRole: String, CaseIterable, Identifiable {
    case owner = "OWNER"
    case admin = "ADMIN"
    case driver = "DRIVER"
    case viewer = "VIEWER"

    var id: String { rawValue }
}
