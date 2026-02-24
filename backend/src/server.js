import "dotenv/config";
import express from "express";
import cors from "cors";
import morgan from "morgan";
import path from "node:path";
import pairingRoutes from "./routes/pairingRoutes.js";
import authRoutes from "./routes/authRoutes.js";
import liveRoutes from "./routes/liveRoutes.js";
import eventRoutes from "./routes/eventRoutes.js";
import ownerRoutes from "./routes/ownerRoutes.js";
import vehicleRoutes from "./routes/vehicleRoutes.js";
import { requireBearer } from "./utils/auth.js";

const app = express();
const port = Number(process.env.PORT || 8787);
const uploadsDir = process.env.CLIP_STORAGE_DIR || "./uploads";

app.use(cors());
app.use(morgan("dev"));
app.use(express.json({ limit: "1mb" }));

app.get("/health", (_req, res) => {
  res.json({ ok: true, ts: Date.now() });
});

app.use("/clips", express.static(path.resolve(uploadsDir)));

app.use("/api/v1", requireBearer);
app.use("/api/v1/auth", authRoutes);
app.use("/api/v1/pairing", pairingRoutes);
app.use("/api/v1/live", liveRoutes);
app.use("/api/v1/owner", ownerRoutes);
app.use("/api/v1/vehicle", vehicleRoutes);
app.use("/api/v1/dashcam/events", eventRoutes);

app.get("/live/viewer", (req, res) => {
  res.type("html").send(`
    <html><body style="font-family:sans-serif;background:#111;color:#eee;padding:16px">
    <h2>Viewer Session</h2>
    <p>Token: ${String(req.query.token || "")}</p>
    <p>Replace this with your hosted WebRTC viewer app.</p>
    </body></html>
  `);
});

app.get("/live/publisher", (req, res) => {
  res.type("html").send(`
    <html><body style="font-family:sans-serif;background:#111;color:#eee;padding:16px">
    <h2>Publisher Session</h2>
    <p>Token: ${String(req.query.token || "")}</p>
    <p>Replace this with your hosted WebRTC publisher app.</p>
    </body></html>
  `);
});

app.get("/live/auto-publisher", (req, res) => {
  const wsUrl = String(req.query.ws_url || "");
  const token = String(req.query.token || "");
  res.type("html").send(`
    <!doctype html>
    <html>
      <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Dashcam Auto Publisher</title>
        <style>
          body { margin: 0; background: #0f1116; color: #e9eef5; font-family: sans-serif; }
          #wrap { padding: 12px; }
          #status { margin-bottom: 10px; font-size: 14px; }
          video { width: 100%; max-height: 70vh; background: #000; border-radius: 10px; object-fit: cover; }
        </style>
      </head>
      <body>
        <div id="wrap">
          <div id="status">Initializing publisher...</div>
          <video id="localVideo" autoplay playsinline muted></video>
        </div>
        <script type="module">
          import { Room, createLocalVideoTrack, createLocalAudioTrack } from "https://cdn.jsdelivr.net/npm/livekit-client/dist/livekit-client.esm.mjs";
          const statusEl = document.getElementById("status");
          const localVideo = document.getElementById("localVideo");
          const wsUrl = ${JSON.stringify(wsUrl)};
          const token = ${JSON.stringify(token)};

          async function start() {
            if (!wsUrl || !token) {
              statusEl.textContent = "Missing ws_url/token";
              return;
            }
            try {
              const room = new Room({
                adaptiveStream: true,
                dynacast: true
              });
              await room.connect(wsUrl, token);
              statusEl.textContent = "Connected. Starting camera + mic...";

              const [videoTrack, audioTrack] = await Promise.all([
                createLocalVideoTrack(),
                createLocalAudioTrack()
              ]);
              await room.localParticipant.publishTrack(videoTrack);
              await room.localParticipant.publishTrack(audioTrack);
              videoTrack.attach(localVideo);
              statusEl.textContent = "Publishing live. Keep this page open.";
            } catch (e) {
              statusEl.textContent = "Publish failed: " + (e?.message || e);
            }
          }

          start();
        </script>
      </body>
    </html>
  `);
});

app.listen(port, () => {
  console.log(`dashcam backend listening on :${port}`);
});
