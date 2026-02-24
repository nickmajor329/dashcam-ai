import { v4 as uuidv4 } from "uuid";

const pairingSessions = new Map();
const pairingsByVehicle = new Map();
const events = [];

const ownerDevicesById = new Map();
const ownerDeviceIndexByToken = new Map();
const ownerRoutingByKey = new Map();
const fleetMembershipByOwner = new Map();
const usersById = new Map();
const userIdByEmail = new Map();

const vehicleCommandQueues = new Map();
const vehicleHealthByKey = new Map();

export function createUser({ email, passwordHash, passwordSalt, displayName = "" }) {
  const normalizedEmail = String(email || "").trim().toLowerCase();
  if (!normalizedEmail) return { error: "invalid_email" };
  if (userIdByEmail.has(normalizedEmail)) return { error: "email_in_use" };

  const id = `usr-${uuidv4()}`;
  const now = Date.now();
  const user = {
    id,
    email: normalizedEmail,
    displayName: displayName || normalizedEmail.split("@")[0],
    passwordHash,
    passwordSalt,
    createdAt: now,
    updatedAt: now
  };
  usersById.set(id, user);
  userIdByEmail.set(normalizedEmail, id);
  return { user };
}

export function getUserByEmail(email) {
  const normalizedEmail = String(email || "").trim().toLowerCase();
  const id = userIdByEmail.get(normalizedEmail);
  return id ? usersById.get(id) ?? null : null;
}

export function getUserById(userId) {
  return usersById.get(String(userId || "").trim()) ?? null;
}

export function createPairingSession(vehicleId) {
  const pairingToken = uuidv4();
  pairingSessions.set(pairingToken, {
    pairingToken,
    vehicleId,
    createdAt: Date.now(),
    consumed: false
  });
  return pairingToken;
}

function upsertFleetMembership(ownerId, vehicleId, role = "OWNER") {
  const memberships = fleetMembershipByOwner.get(ownerId) ?? [];
  const existingIndex = memberships.findIndex((m) => m.vehicleId === vehicleId);
  const next = {
    ownerId,
    vehicleId,
    role,
    updatedAt: Date.now()
  };
  if (existingIndex >= 0) {
    memberships[existingIndex] = { ...memberships[existingIndex], ...next };
  } else {
    memberships.push({ ...next, addedAt: Date.now() });
  }
  fleetMembershipByOwner.set(ownerId, memberships);
}

export function consumePairingSession(pairingToken, ownerId = `owner-${uuidv4()}`, role = "OWNER") {
  const session = pairingSessions.get(pairingToken);
  if (!session || session.consumed) return null;

  session.consumed = true;
  pairingsByVehicle.set(session.vehicleId, {
    vehicleId: session.vehicleId,
    ownerId,
    role,
    pairedAt: Date.now()
  });
  upsertFleetMembership(ownerId, session.vehicleId, role);

  return {
    vehicleId: session.vehicleId,
    ownerId,
    role
  };
}

export function getPairingByVehicle(vehicleId) {
  return pairingsByVehicle.get(vehicleId) ?? null;
}

export function addEvent(event) {
  events.push(event);
}

export function listEvents() {
  return [...events];
}

export function registerOwnerDevice({ owner_id, platform, push_token, device_name }) {
  const existingId = ownerDeviceIndexByToken.get(push_token);
  if (existingId) {
    const prev = ownerDevicesById.get(existingId);
    ownerDevicesById.set(existingId, {
      ...prev,
      ownerId: owner_id,
      platform,
      pushToken: push_token,
      deviceName: device_name || "",
      updatedAt: Date.now()
    });
    return existingId;
  }

  const id = `dev-${uuidv4()}`;
  ownerDevicesById.set(id, {
    id,
    ownerId: owner_id,
    platform,
    pushToken: push_token,
    deviceName: device_name || "",
    createdAt: Date.now(),
    updatedAt: Date.now()
  });
  ownerDeviceIndexByToken.set(push_token, id);
  return id;
}

export function getOwnerDevice(deviceId) {
  return ownerDevicesById.get(deviceId) ?? null;
}

export function upsertOwnerRouting({
  owner_id,
  vehicle_id,
  app_enabled,
  email_enabled,
  sms_enabled,
  email,
  phone,
  target_app_device_id
}) {
  const key = `${owner_id}::${vehicle_id}`;
  ownerRoutingByKey.set(key, {
    ownerId: owner_id,
    vehicleId: vehicle_id,
    appEnabled: app_enabled,
    emailEnabled: email_enabled,
    smsEnabled: sms_enabled,
    email: email || "",
    phone: phone || "",
    targetAppDeviceId: target_app_device_id || "",
    updatedAt: Date.now()
  });
}

export function getOwnerRouting(ownerId, vehicleId) {
  return ownerRoutingByKey.get(`${ownerId}::${vehicleId}`) ?? null;
}

export function listFleetMembership(ownerId) {
  const memberships = fleetMembershipByOwner.get(ownerId) ?? [];
  return [...memberships].sort((a, b) => b.updatedAt - a.updatedAt);
}

export function setFleetRole(ownerId, vehicleId, role) {
  upsertFleetMembership(ownerId, vehicleId, role);
  const pairing = pairingsByVehicle.get(vehicleId);
  if (pairing && pairing.ownerId === ownerId) {
    pairingsByVehicle.set(vehicleId, { ...pairing, role, pairedAt: pairing.pairedAt ?? Date.now() });
  }
}

export function enqueueVehicleCommand({ owner_id, vehicle_id, command_type, payload }) {
  const queue = vehicleCommandQueues.get(vehicle_id) ?? [];
  const cmd = {
    commandId: `cmd-${uuidv4()}`,
    ownerId: owner_id,
    vehicleId: vehicle_id,
    commandType: command_type,
    payload: payload || {},
    createdAt: Date.now(),
    deliveredAt: null,
    deliveredTo: "",
    resultStatus: "PENDING",
    resultMessage: "",
    executedAt: null,
    resultPayload: null
  };
  queue.push(cmd);
  vehicleCommandQueues.set(vehicle_id, queue);
  return cmd;
}

export function popNextVehicleCommand(vehicleId, sourceDevice = "") {
  const queue = vehicleCommandQueues.get(vehicleId) ?? [];
  const index = queue.findIndex((c) => c.deliveredAt == null);
  if (index < 0) return null;
  queue[index].deliveredAt = Date.now();
  queue[index].deliveredTo = sourceDevice || "";
  vehicleCommandQueues.set(vehicleId, queue);
  return queue[index];
}

export function applyVehicleCommandResult({
  command_id,
  status,
  message,
  executed_at,
  result_payload
}) {
  for (const [vehicleId, queue] of vehicleCommandQueues.entries()) {
    const index = queue.findIndex((c) => c.commandId === command_id);
    if (index < 0) continue;
    queue[index].resultStatus = status;
    queue[index].resultMessage = message || "";
    queue[index].executedAt = executed_at || Date.now();
    queue[index].resultPayload = result_payload || null;
    vehicleCommandQueues.set(vehicleId, queue);
    return queue[index];
  }
  return null;
}

export function recordVehicleHealth({
  vehicle_id,
  source_device,
  recording_active,
  free_bytes,
  battery_pct,
  app_version,
  note
}) {
  const key = `${vehicle_id}::${source_device || "unknown"}`;
  const health = {
    vehicleId: vehicle_id,
    sourceDevice: source_device || "",
    recordingActive: !!recording_active,
    freeBytes: Number(free_bytes ?? 0),
    batteryPct: battery_pct == null ? null : Number(battery_pct),
    appVersion: app_version || "",
    note: note || "",
    updatedAt: Date.now()
  };
  vehicleHealthByKey.set(key, health);
  return health;
}

export function listVehicleHealth(vehicleId) {
  const out = [];
  for (const health of vehicleHealthByKey.values()) {
    if (health.vehicleId === vehicleId) {
      out.push(health);
    }
  }
  return out.sort((a, b) => b.updatedAt - a.updatedAt);
}

export function listVehicleCommands(vehicleId) {
  const queue = vehicleCommandQueues.get(vehicleId) ?? [];
  return [...queue].sort((a, b) => b.createdAt - a.createdAt);
}
