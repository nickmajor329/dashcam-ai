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

app.listen(port, () => {
  console.log(`dashcam backend listening on :${port}`);
});
