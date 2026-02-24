# DashcamAI Android MVP

Initial scaffold for a parked-mode dashcam app with AI-triggered remote alerts.

## Current state

- Kotlin Android app module (`app`)
- Dedicated Settings page for configurable modes
- Foreground recording service with CameraX back-camera MP4 capture
- Configurable clip segmentation + rolling retention enforcement
- Pairing flow with QR generation/scanning
- Live View screen backed by server-issued live session URL
- Live View upgrades: bitrate profiles, native-session request path with fallback, and snapshot capture
- Remote camera switching supported via backend vehicle commands (`SET_LENS`)
- Remote command execution supports start/stop recording, horn/light relay hooks, app reboot, and health ping
- Health watchdog in recording service monitors temp/storage/camera failures, attempts self-recovery, and posts owner alerts
- Impact/crash trigger with automatic locked clip preservation and alert event
- Manual parked/driving mode controls with persisted parked GPS snapshot
- Automatic parked/driving transitions from power disconnect/connect broadcasts
- Bluetooth car disconnect heuristic to arm away alerts automatically
- Idle-motion detector (accelerometer) auto-switches parked/driving
- Alert arming toggle (`Owner Away`) persisted across app restarts
- AI event gating: alerts only enqueue when `parked && ownerAwayEnabled`
- TensorFlow Lite inference pipeline on finalized clip previews
- Smarter AI inference: multi-frame clip voting, state heuristics (person/plate/door), and false-positive suppression
- Room event persistence model
- WorkManager alert uploader with network constraints + retry
- Backend multipart upload client for clip + event metadata (backend sends FCM)
- In-app routing config for app/email/sms recipients
- Privacy modes: blur-face/plate intent flags on upload metadata, encrypted event clips at rest, and severity-based event retention policy

## Build prerequisites

- Android Studio Iguana+ (or equivalent)
- Android SDK 35
- JDK 17
- Gradle wrapper (generate once via Android Studio: open project and sync)

## Config

Set backend config in your `~/.gradle/gradle.properties` (or project `gradle.properties`):

- `ALERT_API_BASE_URL=https://your-api.example.com`
- `ALERT_API_KEY=optional_service_key`

The uploader posts multipart form data to:

- `POST /api/v1/dashcam/events`

Fields sent: `event_id`, `event_type`, `created_at_ms`, `source_device`, and `clip` (MP4).
Routing fields also sent: `route_app_enabled`, `route_email_enabled`, `route_sms_enabled`,
`target_app_device_id`, `target_email`, `target_phone`.

Pairing + live endpoints expected by the app:

- `POST /api/v1/pairing/session/start` body: `{ "vehicle_id": "..." }`
  returns: `{ "pairing_token": "..." }`
- `POST /api/v1/pairing/session/complete` body: `{ "pairing_token": "..." }`
  returns: `{ "owner_id": "..." }`
- `POST /api/v1/live/session/url` body:
  `{ "role": "viewer|publisher", "vehicle_id": "...", "owner_id": "..." }`
  returns: `{ "live_url": "https://..." }`

Place your TensorFlow Lite model at:

- `app/src/main/assets/vehicle_event_detector.tflite`

Model contract is documented in:

- `app/src/main/assets/vehicle_event_detector.README.txt`

## Settings page

Open `Open Settings` from the main screen to configure:

- Default camera lens (front/back)
- Clip segment duration (seconds)
- Max local storage cap (GB)
- Idle auto-park enable/disable and threshold (minutes)
- Default display mode toggles (dim/black)
- Privacy toggles: face/plate blur flags, encrypted event clips, and retention days by severity

## Next implementation targets

1. Tune idle-motion thresholds and add debounce windows for rough roads.
2. Add secure auth rotation and clip encryption-at-rest/upload.
3. Add reboot resilience, thermal throttling, and storage health checks.

## Backend template

A runnable backend scaffold now exists at:

- `/Users/Nick_1/Documents/Dashcam/backend`

See:

- `/Users/Nick_1/Documents/Dashcam/backend/README.md`

## iOS companion

A SwiftUI iOS companion module (account + pairing) is available at:

- `/Users/Nick_1/Documents/Dashcam/ios`
