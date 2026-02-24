import SwiftUI

struct LiveView: View {
    @StateObject var viewModel: LiveViewModel

    var body: some View {
        NavigationStack {
            VStack(spacing: 12) {
                TextField("Vehicle ID", text: $viewModel.vehicleId)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    .padding(10)
                    .background(Color(.secondarySystemBackground))
                    .clipShape(RoundedRectangle(cornerRadius: 8))

                Picker("Bitrate", selection: $viewModel.bitrateKbps) {
                    Text("Low 500").tag(500)
                    Text("Med 1200").tag(1200)
                    Text("High 2500").tag(2500)
                }
                .pickerStyle(.segmented)

                Button(action: {
                    Task { await viewModel.openLive() }
                }) {
                    Text(viewModel.isLoading ? "Connecting..." : "Open Live View")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .disabled(viewModel.isLoading)

                if !viewModel.statusMessage.isEmpty {
                    Text(viewModel.statusMessage)
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                }

                if let url = viewModel.viewerURL {
                    WebView(url: url)
                        .clipShape(RoundedRectangle(cornerRadius: 10))
                } else {
                    Spacer()
                }
            }
            .padding()
            .navigationTitle("Live View")
        }
    }
}
