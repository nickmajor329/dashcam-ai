# Dashcam Backend Template

Minimal Node/Express backend for the Dashcam Android app:

- Pairing QR sessions
- Live session URL issuance
- Event upload + clip storage
- Optional fanout to FCM, Twilio SMS, SendGrid email
- Owner device registration (iOS/Android)
- Owner routing preferences per vehicle

## 1) Install

```bash
cd backend
npm install
cp .env.example .env
```

## 2) Configure `.env`

Required for app auth:

- `API_BEARER_TOKEN` (must match Android `ALERT_API_KEY`)
- `PUBLIC_BASE_URL` (base API URL reachable by phone)

Optional but recommended:

- `LIVE_BASE_URL` (where your real live-view web app is hosted)
- `LIVEKIT_URL`, `LIVEKIT_API_KEY`, `LIVEKIT_API_SECRET` (for native WebRTC token issuing)
- `TWILIO_*`
- `SENDGRID_*`
- `FIREBASE_SERVICE_ACCOUNT_JSON`
- `JWT_SECRET` (for account token signing)

## 3) Run

```bash
npm run dev
```

Health check:

```bash
curl http://localhost:8787/health
```

## 4) Android app config

Set in `~/.gradle/gradle.properties`:

```properties
ALERT_API_BASE_URL=http://<your-host>:8787
ALERT_API_KEY=<same as API_BEARER_TOKEN>
```

When user accounts are enabled, clients send:
- `X-Api-Key: <API_BEARER_TOKEN>`
- `Authorization: Bearer <user_jwt>` (for account-scoped endpoints)

## Endpoints

- `POST /api/v1/pairing/session/start`
  - body: `{ "vehicle_id": "dashcam-car-01" }`
  - returns: `{ "pairing_token": "..." }`

- `POST /api/v1/pairing/session/complete`
  - body: `{ "pairing_token": "...", "owner_id": "...", "role": "OWNER|ADMIN|DRIVER|VIEWER" }`
  - returns: `{ "vehicle_id": "...", "owner_id": "...", "role": "...", "paired": true }`

- `POST /api/v1/auth/register`
  - body: `{ "email":"...", "password":"...", "display_name":"..." }`
  - returns: `{ "ok": true, "token":"...", "user": {...} }`

- `POST /api/v1/auth/login`
  - body: `{ "email":"...", "password":"..." }`
  - returns: `{ "ok": true, "token":"...", "user": {...} }`

- `POST /api/v1/auth/me`
  - requires `Authorization: Bearer <user_jwt>`
  - returns current account identity

- `POST /api/v1/owner/devices/register`
  - body: `{ "owner_id":"...", "platform":"ios|android", "push_token":"...", "device_name":"..." }`
  - returns: `{ "ok": true, "device_id": "..." }`

- `POST /api/v1/owner/routing/update`
  - body:
    `{ "owner_id":"...", "vehicle_id":"...", "app_enabled":true, "email_enabled":false,
       "sms_enabled":false, "email":"...", "phone":"...", "target_app_device_id":"..." }`

- `GET /api/v1/owner/fleet/:ownerId`
  - returns owner fleet membership list with roles

- `POST /api/v1/owner/fleet/role/update`
  - body: `{ "owner_id":"...", "vehicle_id":"...", "role":"OWNER|ADMIN|DRIVER|VIEWER" }`

- `POST /api/v1/live/session/url`
  - body: `{ "role": "viewer|publisher", "vehicle_id": "...", "owner_id": "...", "bitrate_kbps": 1200 }`
  - returns: `{ "live_url": "https://..." }`

- `POST /api/v1/live/session/native`
  - body: `{ "role": "viewer|publisher", "vehicle_id": "...", "owner_id": "...", "bitrate_kbps": 1200 }`
  - returns native session support status and fallback URL
  - if `LIVEKIT_*` env vars are set, returns:
    - `supported=true`
    - `provider=livekit`
    - `ws_url`, `room_name`, `participant_identity`, `access_token`

- `POST /api/v1/dashcam/events`
  - multipart fields from Android app + `clip` file
  - privacy metadata flags may be included:
    - `privacy_blur_faces=true|false`
    - `privacy_blur_plates=true|false`

- `POST /api/v1/vehicle/commands`
  - body:
    `{ "owner_id":"...", "vehicle_id":"...", "command_type":"...", "payload":{...} }`
  - command types:
    - `SET_LENS` payload `{ "lens":"front|back" }`
    - `START_RECORDING` payload `{}`
    - `STOP_RECORDING` payload `{}`
    - `HORN_RELAY` payload `{ "duration_ms": 1200 }` (optional)
    - `LIGHT_RELAY` payload `{ "state":"on|off" }`
    - `REBOOT_APP` payload `{}`
    - `HEALTH_PING` payload `{ "note":"optional" }`
  - returns: `{ "ok": true, "command_id": "..." }`

- `POST /api/v1/vehicle/commands/next`
  - body: `{ "vehicle_id":"...", "source_device":"..." }`
  - returns either `{ "has_command": false }` or a command payload

- `POST /api/v1/vehicle/commands/result`
  - body:
    `{ "command_id":"...", "status":"OK|FAILED|UNSUPPORTED", "message":"...", "executed_at":1730000000000, "result_payload":{} }`
  - returns: `{ "ok": true }`

- `GET /api/v1/vehicle/commands/history/:vehicleId`
  - returns queued+delivered commands with execution result fields

- `POST /api/v1/vehicle/health`
  - body:
    `{ "vehicle_id":"...", "source_device":"...", "recording_active":true, "free_bytes":12345, "battery_pct":86, "app_version":"0.1.0", "note":"" }`
  - returns: `{ "ok": true, "health": {...} }`

- `GET /api/v1/vehicle/health/:vehicleId`
  - returns latest health pings for a vehicle

- `POST /api/v1/vehicle/watchdog/alert`
  - body:
    `{ "vehicle_id":"...", "owner_id":"...", "source_device":"...", "reason":"LOW_STORAGE|HIGH_TEMP|CAMERA_FAILURE|SERVICE_RECOVERY", "severity":"INFO|WARN|CRITICAL", "message":"...", "details":{}, "route_app_enabled":true, "route_email_enabled":false, "route_sms_enabled":false, "target_app_device_id":"...", "target_email":"...", "target_phone":"..." }`
  - sends push/email/SMS using routing config and returns `{ "ok": true }`

## Notes

- This template uses in-memory stores (non-persistent).
- Replace with Postgres/Redis + signed JWTs for production.
- Replace `/live/viewer` and `/live/publisher` placeholder pages with your actual WebRTC app.

## Render Deploy (No Mac Required)

1. Push this repo to GitHub.
2. In Render, create a new Blueprint using repo root `render.yaml`.
3. After first deploy, set:
   - `PUBLIC_BASE_URL` = your Render URL (example `https://dashcam-backend.onrender.com`)
   - optional `LIVE_BASE_URL` for hosted web viewer/publisher pages
   - optional `LIVEKIT_URL`, `LIVEKIT_API_KEY`, `LIVEKIT_API_SECRET` for native WebRTC token flow
4. Redeploy service.
5. Verify:
   - `GET https://<your-render-url>/health`

Client config:
- Android `ALERT_API_BASE_URL=https://<your-render-url>`
- Android `ALERT_API_KEY=<API_BEARER_TOKEN>`
- iOS `backendBaseURL=https://<your-render-url>` and `apiKey=<API_BEARER_TOKEN>`

Live from Android to iPhone anywhere:
1. Android dashcam app uses `/api/v1/live/session/native` for live session request.
2. iPhone app also requests `/api/v1/live/session/native`.
3. Backend returns LiveKit token payload (`ws_url`, `access_token`) when `LIVEKIT_*` is configured.
4. Clients open LiveKit viewer/publisher experience using those credentials.

## Quick Test (curl)

Use your actual token in `Authorization: Bearer ...`.

1. Register iPhone push token

```bash
curl -X POST http://localhost:8787/api/v1/owner/devices/register \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "owner_id":"owner-123",
    "platform":"ios",
    "push_token":"ios_push_token_here",
    "device_name":"Nick iPhone"
  }'
```

2. Save routing preferences

```bash
curl -X POST http://localhost:8787/api/v1/owner/routing/update \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "owner_id":"owner-123",
    "vehicle_id":"dashcam-car-01",
    "app_enabled":true,
    "email_enabled":true,
    "sms_enabled":true,
    "email":"you@example.com",
    "phone":"+15551234567",
    "target_app_device_id":"dev-REPLACE_FROM_REGISTER_RESPONSE"
  }'
```

3. Request live viewer URL

```bash
curl -X POST http://localhost:8787/api/v1/live/session/url \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "role":"viewer",
    "vehicle_id":"dashcam-car-01",
    "owner_id":"owner-123"
  }'
```

4. Send remote command (examples)

```bash
curl -X POST http://localhost:8787/api/v1/vehicle/commands \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "owner_id":"owner-123",
    "vehicle_id":"dashcam-car-01",
    "command_type":"SET_LENS",
    "payload":{"lens":"front"}
  }'
```

```bash
curl -X POST http://localhost:8787/api/v1/vehicle/commands \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "owner_id":"owner-123",
    "vehicle_id":"dashcam-car-01",
    "command_type":"START_RECORDING",
    "payload":{}
  }'
```

```bash
curl -X POST http://localhost:8787/api/v1/vehicle/commands \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "owner_id":"owner-123",
    "vehicle_id":"dashcam-car-01",
    "command_type":"HEALTH_PING",
    "payload":{"note":"manual check"}
  }'
```

5. Trigger watchdog alert manually

```bash
curl -X POST http://localhost:8787/api/v1/vehicle/watchdog/alert \
  -H "Authorization: Bearer replace_me" \
  -H "Content-Type: application/json" \
  -d '{
    "vehicle_id":"dashcam-car-01",
    "owner_id":"owner-123",
    "source_device":"android-device-1",
    "reason":"LOW_STORAGE",
    "severity":"WARN",
    "message":"Storage low in watchdog test",
    "details":{"free_bytes":123456789},
    "route_app_enabled":true,
    "route_email_enabled":false,
    "route_sms_enabled":false,
    "target_app_device_id":"dev-REPLACE"
  }'
```
