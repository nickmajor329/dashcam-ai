import express from "express";
import { z } from "zod";
import crypto from "node:crypto";

const router = express.Router();

function base64UrlJson(obj) {
  return Buffer.from(JSON.stringify(obj)).toString("base64url");
}

function signHs256(input, secret) {
  return crypto.createHmac("sha256", secret).update(input).digest("base64url");
}

function buildLiveKitToken({
  apiKey,
  apiSecret,
  identity,
  roomName,
  role,
  expiresInSeconds
}) {
  const now = Math.floor(Date.now() / 1000);
  const header = { alg: "HS256", typ: "JWT" };
  const payload = {
    iss: apiKey,
    sub: identity,
    nbf: now - 5,
    exp: now + expiresInSeconds,
    video: {
      room: roomName,
      roomJoin: true,
      canPublish: role === "publisher",
      canSubscribe: true,
      canPublishData: true
    }
  };

  const encodedHeader = base64UrlJson(header);
  const encodedPayload = base64UrlJson(payload);
  const signingInput = `${encodedHeader}.${encodedPayload}`;
  const signature = signHs256(signingInput, apiSecret);
  return `${signingInput}.${signature}`;
}

router.post("/session/url", (req, res) => {
  const schema = z.object({
    role: z.enum(["viewer", "publisher"]),
    vehicle_id: z.string().min(1),
    owner_id: z.string().optional().default(""),
    bitrate_kbps: z.number().int().optional().default(1200)
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const base = (process.env.LIVE_BASE_URL || process.env.PUBLIC_BASE_URL || "http://localhost:8787").trim().replace(/\/$/, "");
  const tokenPayload = Buffer.from(
    JSON.stringify({
      role: parsed.data.role,
      vehicle_id: parsed.data.vehicle_id,
      owner_id: parsed.data.owner_id,
      bitrate_kbps: parsed.data.bitrate_kbps,
      ts: Date.now()
    })
  ).toString("base64url");

  const liveUrl = `${base}/live/${parsed.data.role}?token=${tokenPayload}`;
  return res.json({ live_url: liveUrl, expires_in_seconds: 120 });
});

router.post("/session/native", (req, res) => {
  const schema = z.object({
    role: z.enum(["viewer", "publisher"]),
    vehicle_id: z.string().min(1),
    owner_id: z.string().optional().default(""),
    bitrate_kbps: z.number().int().optional().default(1200)
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const base = (process.env.LIVE_BASE_URL || process.env.PUBLIC_BASE_URL || "http://localhost:8787").trim().replace(/\/$/, "");
  const liveKitUrl = (process.env.LIVEKIT_URL || "").trim();
  const liveKitApiKey = (process.env.LIVEKIT_API_KEY || "").trim();
  const liveKitApiSecret = (process.env.LIVEKIT_API_SECRET || "").trim();

  if (liveKitUrl && liveKitApiKey && liveKitApiSecret) {
    const expiresInSeconds = 120;
    const roomName = `vehicle-${parsed.data.vehicle_id}`;
    const identityBase = parsed.data.owner_id || parsed.data.vehicle_id;
    const identity = `${parsed.data.role}-${identityBase}-${Date.now()}`;
    const accessToken = buildLiveKitToken({
      apiKey: liveKitApiKey,
      apiSecret: liveKitApiSecret,
      identity,
      roomName,
      role: parsed.data.role,
      expiresInSeconds
    });

    return res.json({
      supported: true,
      provider: "livekit",
      ws_url: liveKitUrl,
      room_name: roomName,
      participant_identity: identity,
      role: parsed.data.role,
      access_token: accessToken,
      expires_in_seconds: expiresInSeconds
    });
  }

  const tokenPayload = Buffer.from(
    JSON.stringify({
      role: parsed.data.role,
      vehicle_id: parsed.data.vehicle_id,
      owner_id: parsed.data.owner_id,
      bitrate_kbps: parsed.data.bitrate_kbps,
      transport: "native-webrtc",
      ts: Date.now()
    })
  ).toString("base64url");
  const fallbackLiveUrl = `${base}/live/${parsed.data.role}?token=${tokenPayload}`;
  return res.json({
    supported: false,
    reason: "Native WebRTC signaling is not configured on backend",
    fallback_live_url: fallbackLiveUrl,
    expires_in_seconds: 120
  });
});

export default router;
