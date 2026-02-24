import Foundation

@MainActor
final class AuthViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""
    @Published var isLoading = false
    @Published var errorMessage = ""

    private let apiClient: APIClient
    private let sessionStore: AuthSessionStore

    init(apiClient: APIClient, sessionStore: AuthSessionStore) {
        self.apiClient = apiClient
        self.sessionStore = sessionStore
    }

    func login() async {
        await authenticate(isRegister: false)
    }

    func register() async {
        await authenticate(isRegister: true)
    }

    private func authenticate(isRegister: Bool) async {
        errorMessage = ""
        guard !email.trimmingCharacters(in: .whitespaces).isEmpty else {
            errorMessage = "Email is required"
            return
        }
        guard password.count >= 8 else {
            errorMessage = "Password must be at least 8 characters"
            return
        }

        isLoading = true
        defer { isLoading = false }

        do {
            let response: AuthResponse
            if isRegister {
                response = try await apiClient.register(email: email, password: password)
            } else {
                response = try await apiClient.login(email: email, password: password)
            }

            sessionStore.save(
                session: AuthSession(
                    userId: response.user.id,
                    email: response.user.email,
                    token: response.token
                )
            )
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    func logout() {
        sessionStore.clear()
    }
}
