import express from "express";
import multer from "multer";
import path from "node:path";
import fs from "node:fs";
import { addEvent, getOwnerDevice, getOwnerRouting } from "../store/memoryStore.js";
import { sendEmailAlert, sendPushAlert, sendSmsAlert } from "../services/notifiers.js";

const router = express.Router();

const storageDir = process.env.CLIP_STORAGE_DIR || "./uploads";
fs.mkdirSync(storageDir, { recursive: true });

const upload = multer({
  storage: multer.diskStorage({
    destination: (_req, _file, cb) => cb(null, storageDir),
    filename: (_req, file, cb) => {
      const safe = `${Date.now()}-${Math.random().toString(36).slice(2)}${path.extname(file.originalname) || ".mp4"}`;
      cb(null, safe);
    }
  }),
  limits: { fileSize: 200 * 1024 * 1024 }
});

router.post("/", upload.single("clip"), async (req, res) => {
  const event = {
    id: req.body.event_id || `${Date.now()}`,
    eventType: req.body.event_type || "UNKNOWN",
    createdAtMs: req.body.created_at_ms || `${Date.now()}`,
    sourceDevice: req.body.source_device || "",
    vehicleId: req.body.vehicle_id || "",
    ownerId: req.body.owner_id || "",
    clipPath: req.file?.path || "",
    routing: {
      appEnabled: req.body.route_app_enabled === "true",
      emailEnabled: req.body.route_email_enabled === "true",
      smsEnabled: req.body.route_sms_enabled === "true",
      appDeviceId: req.body.target_app_device_id || "",
      targetEmail: req.body.target_email || "",
      targetPhone: req.body.target_phone || ""
    },
    privacy: {
      blurFaces: req.body.privacy_blur_faces === "true",
      blurPlates: req.body.privacy_blur_plates === "true"
    }
  };

  const storedRouting = event.ownerId && event.vehicleId
    ? getOwnerRouting(event.ownerId, event.vehicleId)
    : null;

  const resolvedRouting = {
    appEnabled: event.routing.appEnabled || Boolean(storedRouting?.appEnabled),
    emailEnabled: event.routing.emailEnabled || Boolean(storedRouting?.emailEnabled),
    smsEnabled: event.routing.smsEnabled || Boolean(storedRouting?.smsEnabled),
    appDeviceId: event.routing.appDeviceId || storedRouting?.targetAppDeviceId || "",
    targetEmail: event.routing.targetEmail || storedRouting?.email || "",
    targetPhone: event.routing.targetPhone || storedRouting?.phone || ""
  };

  addEvent({ ...event, routing: resolvedRouting });

  const title = `Dashcam alert: ${event.eventType}`;
  const msg = `Event ${event.eventType} at ${new Date(Number(event.createdAtMs)).toISOString()}`;
  const clipUrlBase = (process.env.PUBLIC_BASE_URL || "http://localhost:8787").replace(/\/$/, "");
  const clipUrl = event.clipPath ? `${clipUrlBase}/clips/${path.basename(event.clipPath)}` : "";

  const outcomes = [];

  if (resolvedRouting.appEnabled) {
    const targetDevice = getOwnerDevice(resolvedRouting.appDeviceId);
    const pushToken = targetDevice?.pushToken || resolvedRouting.appDeviceId;
    outcomes.push(sendPushAlert(pushToken, title, msg, { clip_url: clipUrl, event_id: String(event.id) }));
  }
  if (resolvedRouting.emailEnabled) {
    outcomes.push(sendEmailAlert(resolvedRouting.targetEmail, title, `${msg}\nClip: ${clipUrl}`));
  }
  if (resolvedRouting.smsEnabled) {
    outcomes.push(sendSmsAlert(resolvedRouting.targetPhone, `${msg} ${clipUrl}`.trim()));
  }

  await Promise.allSettled(outcomes);

  return res.status(201).json({
    accepted: true,
    event_id: event.id,
    clip_url: clipUrl
  });
});

router.get("/", (_req, res) => {
  return res.json({ ok: true, message: "Use persistent DB in production" });
});

export default router;
