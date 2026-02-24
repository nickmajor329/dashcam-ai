import SwiftUI

struct PairingView: View {
    @ObservedObject var sessionStore: AuthSessionStore
    @StateObject var viewModel: PairingViewModel
    @State private var showScanner = false

    var body: some View {
        NavigationStack {
            VStack(spacing: 12) {
                if let session = sessionStore.session {
                    Text("Signed in: \(session.email)")
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                }

                TextField("Pairing token or QR payload", text: $viewModel.pairingToken)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    .padding(10)
                    .background(Color(.secondarySystemBackground))
                    .clipShape(RoundedRectangle(cornerRadius: 8))

                Picker("Role", selection: $viewModel.selectedRole) {
                    ForEach(FleetRole.allCases) { role in
                        Text(role.rawValue).tag(role)
                    }
                }
                .pickerStyle(.segmented)

                Button("Scan QR") {
                    showScanner = true
                }
                .buttonStyle(.bordered)

                Button(action: {
                    Task { await viewModel.completePairing() }
                }) {
                    Text(viewModel.isLoading ? "Pairing..." : "Complete Pairing")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .disabled(viewModel.isLoading)

                if !viewModel.statusMessage.isEmpty {
                    Text(viewModel.statusMessage)
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                }

                Spacer()
            }
            .padding()
            .navigationTitle("Pair Vehicle")
            .sheet(isPresented: $showScanner) {
                QRCodeScannerView { payload in
                    viewModel.setScannedPayload(payload)
                    showScanner = false
                }
                .ignoresSafeArea()
            }
        }
    }
}
