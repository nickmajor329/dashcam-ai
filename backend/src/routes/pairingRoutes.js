import express from "express";
import { z } from "zod";
import { createPairingSession, consumePairingSession } from "../store/memoryStore.js";
import { optionalUserJwt } from "../utils/auth.js";

const router = express.Router();
router.use(optionalUserJwt);

router.post("/session/start", (req, res) => {
  const schema = z.object({ vehicle_id: z.string().min(1) });
  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const pairingToken = createPairingSession(parsed.data.vehicle_id.trim());
  return res.json({ pairing_token: pairingToken, expires_in_seconds: 300 });
});

router.post("/session/complete", (req, res) => {
  const schema = z.object({
    pairing_token: z.string().min(1),
    owner_id: z.string().optional().default(""),
    role: z.enum(["OWNER", "ADMIN", "DRIVER", "VIEWER"]).optional().default("OWNER")
  });
  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const ownerId = req.user?.id || parsed.data.owner_id.trim() || undefined;
  const result = consumePairingSession(parsed.data.pairing_token.trim(), ownerId, parsed.data.role);
  if (!result) {
    return res.status(404).json({ error: "invalid_or_consumed_token" });
  }

  return res.json({
    vehicle_id: result.vehicleId,
    owner_id: result.ownerId,
    role: result.role,
    paired: true
  });
});

export default router;
