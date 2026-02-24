import express from "express";
import { z } from "zod";
import {
  registerOwnerDevice,
  upsertOwnerRouting,
  getOwnerDevice,
  getOwnerRouting,
  listFleetMembership,
  setFleetRole
} from "../store/memoryStore.js";

const router = express.Router();

router.post("/devices/register", (req, res) => {
  const schema = z.object({
    owner_id: z.string().min(1),
    platform: z.enum(["ios", "android"]),
    push_token: z.string().min(1),
    device_name: z.string().optional().default("")
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const deviceId = registerOwnerDevice(parsed.data);
  const device = getOwnerDevice(deviceId);
  return res.json({ ok: true, device_id: deviceId, device });
});

router.post("/routing/update", (req, res) => {
  const schema = z.object({
    owner_id: z.string().min(1),
    vehicle_id: z.string().min(1),
    app_enabled: z.boolean(),
    email_enabled: z.boolean(),
    sms_enabled: z.boolean(),
    email: z.string().optional().default(""),
    phone: z.string().optional().default(""),
    target_app_device_id: z.string().optional().default("")
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  upsertOwnerRouting(parsed.data);
  const routing = getOwnerRouting(parsed.data.owner_id, parsed.data.vehicle_id);
  return res.json({ ok: true, routing });
});

router.get("/fleet/:ownerId", (req, res) => {
  const ownerId = String(req.params.ownerId || "");
  if (!ownerId) return res.status(400).json({ error: "invalid_owner_id" });
  return res.json({ ok: true, owner_id: ownerId, vehicles: listFleetMembership(ownerId) });
});

router.post("/fleet/role/update", (req, res) => {
  const schema = z.object({
    owner_id: z.string().min(1),
    vehicle_id: z.string().min(1),
    role: z.enum(["OWNER", "ADMIN", "DRIVER", "VIEWER"])
  });
  const parsed = schema.safeParse(req.body);
  if (!parsed.success) return res.status(400).json({ error: "invalid_payload" });
  setFleetRole(parsed.data.owner_id, parsed.data.vehicle_id, parsed.data.role);
  return res.json({ ok: true });
});

export default router;
