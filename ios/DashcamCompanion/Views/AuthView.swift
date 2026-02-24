import SwiftUI

struct AuthView: View {
    @StateObject var viewModel: AuthViewModel

    var body: some View {
        VStack(spacing: 12) {
            Text("Dashcam Account")
                .font(.title2)
                .bold()

            TextField("Email", text: $viewModel.email)
                .textInputAutocapitalization(.never)
                .keyboardType(.emailAddress)
                .autocorrectionDisabled()
                .padding(10)
                .background(Color(.secondarySystemBackground))
                .clipShape(RoundedRectangle(cornerRadius: 8))

            SecureField("Password", text: $viewModel.password)
                .padding(10)
                .background(Color(.secondarySystemBackground))
                .clipShape(RoundedRectangle(cornerRadius: 8))

            if !viewModel.errorMessage.isEmpty {
                Text(viewModel.errorMessage)
                    .foregroundStyle(.red)
                    .font(.footnote)
            }

            Button(action: {
                Task { await viewModel.login() }
            }) {
                Text(viewModel.isLoading ? "Signing in..." : "Sign In")
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .disabled(viewModel.isLoading)

            Button(action: {
                Task { await viewModel.register() }
            }) {
                Text(viewModel.isLoading ? "Creating..." : "Create Account")
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.bordered)
            .disabled(viewModel.isLoading)
        }
        .padding()
    }
}
