import Foundation

enum APIError: LocalizedError {
    case invalidURL
    case requestFailed(String)
    case invalidResponse

    var errorDescription: String? {
        switch self {
        case .invalidURL:
            return "Invalid backend URL"
        case let .requestFailed(message):
            return message
        case .invalidResponse:
            return "Invalid response from server"
        }
    }
}

final class APIClient {
    private let decoder = JSONDecoder()

    private func makeRequest(
        path: String,
        method: String = "POST",
        body: [String: Any],
        userToken: String?
    ) throws -> URLRequest {
        guard let url = URL(string: AppConfig.backendBaseURL + path) else {
            throw APIError.invalidURL
        }

        var request = URLRequest(url: url)
        request.httpMethod = method
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.setValue("application/json", forHTTPHeaderField: "Accept")

        if !AppConfig.apiKey.isEmpty {
            request.setValue(AppConfig.apiKey, forHTTPHeaderField: "X-Api-Key")
        }

        if let token = userToken, !token.isEmpty {
            request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        } else if !AppConfig.apiKey.isEmpty {
            request.setValue("Bearer \(AppConfig.apiKey)", forHTTPHeaderField: "Authorization")
        }

        request.httpBody = try JSONSerialization.data(withJSONObject: body)
        return request
    }

    private func perform<T: Decodable>(_ request: URLRequest, as: T.Type) async throws -> T {
        let (data, response) = try await URLSession.shared.data(for: request)
        guard let http = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }

        guard (200...299).contains(http.statusCode) else {
            let message = String(data: data, encoding: .utf8) ?? "HTTP \(http.statusCode)"
            throw APIError.requestFailed(message)
        }

        do {
            return try decoder.decode(T.self, from: data)
        } catch {
            throw APIError.requestFailed("Decode failed: \(error.localizedDescription)")
        }
    }

    func register(email: String, password: String) async throws -> AuthResponse {
        let request = try makeRequest(
            path: "/api/v1/auth/register",
            body: [
                "email": email,
                "password": password,
                "display_name": email.components(separatedBy: "@").first ?? ""
            ],
            userToken: nil
        )
        return try await perform(request, as: AuthResponse.self)
    }

    func login(email: String, password: String) async throws -> AuthResponse {
        let request = try makeRequest(
            path: "/api/v1/auth/login",
            body: [
                "email": email,
                "password": password
            ],
            userToken: nil
        )
        return try await perform(request, as: AuthResponse.self)
    }

    func completePairing(token: String, role: FleetRole, userToken: String) async throws -> PairingCompleteResponse {
        let request = try makeRequest(
            path: "/api/v1/pairing/session/complete",
            body: [
                "pairing_token": token,
                "role": role.rawValue
            ],
            userToken: userToken
        )
        return try await perform(request, as: PairingCompleteResponse.self)
    }

    func requestNativeLiveSession(
        vehicleId: String,
        ownerId: String,
        role: String = "viewer",
        bitrateKbps: Int = 1200,
        userToken: String
    ) async throws -> NativeLiveSessionResponse {
        let request = try makeRequest(
            path: "/api/v1/live/session/native",
            body: [
                "role": role,
                "vehicle_id": vehicleId,
                "owner_id": ownerId,
                "bitrate_kbps": bitrateKbps
            ],
            userToken: userToken
        )
        return try await perform(request, as: NativeLiveSessionResponse.self)
    }
}
