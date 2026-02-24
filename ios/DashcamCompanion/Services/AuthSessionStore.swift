import Foundation

final class AuthSessionStore: ObservableObject {
    @Published private(set) var session: AuthSession?

    private let accountKey = "auth_session"

    init() {
        load()
    }

    func load() {
        guard let data = KeychainStore.load(account: accountKey) else {
            session = nil
            return
        }
        session = try? JSONDecoder().decode(AuthSession.self, from: data)
    }

    func save(session: AuthSession) {
        guard let data = try? JSONEncoder().encode(session) else { return }
        if KeychainStore.save(data, for: accountKey) {
            self.session = session
        }
    }

    func clear() {
        KeychainStore.delete(account: accountKey)
        session = nil
    }
}
