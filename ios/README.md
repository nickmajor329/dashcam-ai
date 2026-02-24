# iOS Companion App (Account + Pairing)

This folder contains a SwiftUI iOS client module for:
- account register/login
- secure JWT session storage in Keychain
- authenticated pairing completion using QR token
- backend header model: `X-Api-Key` + `Authorization: Bearer <user_jwt>`
- live view open flow via `/api/v1/live/session/native` and LiveKit hosted viewer URL

## Files

- `DashcamCompanionApp.swift`: app entrypoint
- `Config.swift`: backend URL + API key
- `Models.swift`: API models
- `Services/APIClient.swift`: auth + pairing calls
- `Services/KeychainStore.swift`: secure token storage
- `Services/AuthSessionStore.swift`: session state
- `ViewModels/AuthViewModel.swift`
- `ViewModels/PairingViewModel.swift`
- `ViewModels/LiveViewModel.swift`
- `Views/AuthView.swift`
- `Views/PairingView.swift`
- `Views/LiveView.swift`
- `Views/WebView.swift`
- `Views/QRCodeScannerView.swift`
- `Views/ContentView.swift`

## Integrate into Xcode

1. Create a new iOS App project in Xcode (SwiftUI, Swift).
2. Add all files from `ios/DashcamCompanion/` into your app target.
3. Set `AppConfig.backendBaseURL` and `AppConfig.apiKey` in `Config.swift`.
4. In your app target Info settings, add:
   - `Privacy - Camera Usage Description` (`NSCameraUsageDescription`)
5. Build and run on device.

Alternative with XcodeGen:
1. Install [XcodeGen](https://github.com/yonaskolb/XcodeGen).
2. From `ios/`, run `xcodegen generate`.
3. Open `DashcamCompanion.xcodeproj`.
4. Set `backendBaseURL` and `apiKey` in `Config.swift`.
   - Example:
     - `backendBaseURL = "https://your-render-service.onrender.com"`
     - `apiKey = "<API_BEARER_TOKEN>"`

## Backend expectations

The backend should expose:
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `POST /api/v1/pairing/session/complete`
- `POST /api/v1/live/session/native`

And should accept:
- `X-Api-Key: <API_BEARER_TOKEN>`
- `Authorization: Bearer <user_jwt>`

For true low-latency live view, configure backend env:
- `LIVEKIT_URL`
- `LIVEKIT_API_KEY`
- `LIVEKIT_API_SECRET`
