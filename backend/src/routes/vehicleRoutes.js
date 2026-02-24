import express from "express";
import { z } from "zod";
import {
  enqueueVehicleCommand,
  popNextVehicleCommand,
  applyVehicleCommandResult,
  recordVehicleHealth,
  listVehicleHealth,
  listVehicleCommands,
  getOwnerDevice,
  getOwnerRouting
} from "../store/memoryStore.js";
import { sendEmailAlert, sendPushAlert, sendSmsAlert } from "../services/notifiers.js";

const router = express.Router();

router.post("/commands", (req, res) => {
  const schema = z.object({
    owner_id: z.string().min(1),
    vehicle_id: z.string().min(1),
    command_type: z.enum([
      "SET_LENS",
      "START_RECORDING",
      "STOP_RECORDING",
      "HORN_RELAY",
      "LIGHT_RELAY",
      "REBOOT_APP",
      "HEALTH_PING"
    ]),
    payload: z.record(z.any()).optional().default({})
  }).superRefine((val, ctx) => {
    if (val.command_type === "SET_LENS") {
      const lens = val.payload?.lens;
      if (lens !== "front" && lens !== "back") {
        ctx.addIssue({ code: z.ZodIssueCode.custom, message: "SET_LENS requires payload.lens front|back" });
      }
    }
    if (val.command_type === "LIGHT_RELAY") {
      const state = val.payload?.state;
      if (state !== "on" && state !== "off") {
        ctx.addIssue({ code: z.ZodIssueCode.custom, message: "LIGHT_RELAY requires payload.state on|off" });
      }
    }
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const cmd = enqueueVehicleCommand(parsed.data);
  return res.status(201).json({ ok: true, command_id: cmd.commandId });
});

router.post("/commands/next", (req, res) => {
  const schema = z.object({
    vehicle_id: z.string().min(1),
    source_device: z.string().optional().default("")
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const cmd = popNextVehicleCommand(parsed.data.vehicle_id, parsed.data.source_device);
  if (!cmd) {
    return res.json({ has_command: false });
  }

  return res.json({
    has_command: true,
    command_id: cmd.commandId,
    command_type: cmd.commandType,
    payload: cmd.payload
  });
});

router.post("/commands/result", (req, res) => {
  const schema = z.object({
    command_id: z.string().min(1),
    status: z.enum(["OK", "FAILED", "UNSUPPORTED"]),
    message: z.string().optional().default(""),
    executed_at: z.number().int().optional().default(Date.now()),
    result_payload: z.record(z.any()).optional().default({})
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const cmd = applyVehicleCommandResult(parsed.data);
  if (!cmd) {
    return res.status(404).json({ error: "command_not_found" });
  }
  return res.json({ ok: true });
});

router.get("/commands/history/:vehicleId", (req, res) => {
  const vehicleId = String(req.params.vehicleId || "");
  if (!vehicleId) return res.status(400).json({ error: "invalid_vehicle_id" });
  return res.json({ ok: true, items: listVehicleCommands(vehicleId) });
});

router.post("/health", (req, res) => {
  const schema = z.object({
    vehicle_id: z.string().min(1),
    source_device: z.string().optional().default(""),
    recording_active: z.boolean().optional().default(false),
    free_bytes: z.number().optional().default(0),
    battery_pct: z.number().nullable().optional().default(null),
    app_version: z.string().optional().default(""),
    note: z.string().optional().default("")
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const health = recordVehicleHealth(parsed.data);
  return res.json({ ok: true, health });
});

router.get("/health/:vehicleId", (req, res) => {
  const vehicleId = String(req.params.vehicleId || "");
  if (!vehicleId) return res.status(400).json({ error: "invalid_vehicle_id" });
  return res.json({ ok: true, items: listVehicleHealth(vehicleId) });
});

router.post("/watchdog/alert", async (req, res) => {
  const schema = z.object({
    vehicle_id: z.string().min(1),
    owner_id: z.string().optional().default(""),
    source_device: z.string().optional().default(""),
    reason: z.enum(["LOW_STORAGE", "HIGH_TEMP", "CAMERA_FAILURE", "SERVICE_RECOVERY"]),
    severity: z.enum(["INFO", "WARN", "CRITICAL"]).optional().default("WARN"),
    message: z.string().min(1),
    details: z.record(z.any()).optional().default({}),
    route_app_enabled: z.boolean().optional().default(false),
    route_email_enabled: z.boolean().optional().default(false),
    route_sms_enabled: z.boolean().optional().default(false),
    target_app_device_id: z.string().optional().default(""),
    target_email: z.string().optional().default(""),
    target_phone: z.string().optional().default("")
  });

  const parsed = schema.safeParse(req.body);
  if (!parsed.success) {
    return res.status(400).json({ error: "invalid_payload" });
  }

  const data = parsed.data;
  const storedRouting = data.owner_id ? getOwnerRouting(data.owner_id, data.vehicle_id) : null;
  const resolvedRouting = {
    appEnabled: data.route_app_enabled || Boolean(storedRouting?.appEnabled),
    emailEnabled: data.route_email_enabled || Boolean(storedRouting?.emailEnabled),
    smsEnabled: data.route_sms_enabled || Boolean(storedRouting?.smsEnabled),
    appDeviceId: data.target_app_device_id || storedRouting?.targetAppDeviceId || "",
    targetEmail: data.target_email || storedRouting?.email || "",
    targetPhone: data.target_phone || storedRouting?.phone || ""
  };

  const title = `Dashcam Watchdog: ${data.reason}`;
  const body = `${data.severity}: ${data.message}`;
  const payloadData = {
    vehicle_id: data.vehicle_id,
    reason: data.reason,
    severity: data.severity,
    source_device: data.source_device
  };

  const tasks = [];
  if (resolvedRouting.appEnabled) {
    const targetDevice = getOwnerDevice(resolvedRouting.appDeviceId);
    const pushToken = targetDevice?.pushToken || resolvedRouting.appDeviceId;
    tasks.push(sendPushAlert(pushToken, title, body, payloadData));
  }
  if (resolvedRouting.emailEnabled) {
    tasks.push(sendEmailAlert(resolvedRouting.targetEmail, title, `${body}\n${JSON.stringify(data.details)}`));
  }
  if (resolvedRouting.smsEnabled) {
    tasks.push(sendSmsAlert(resolvedRouting.targetPhone, `${title} ${body}`));
  }
  await Promise.allSettled(tasks);

  return res.json({ ok: true });
});

export default router;
