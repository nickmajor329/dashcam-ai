import crypto from "node:crypto";

function base64UrlEncode(input) {
  const raw = Buffer.isBuffer(input) ? input : Buffer.from(String(input));
  return raw.toString("base64url");
}

function base64UrlDecode(input) {
  return Buffer.from(String(input), "base64url").toString("utf8");
}

function secret() {
  return process.env.JWT_SECRET?.trim() || "dev-jwt-secret-change-me";
}

export function signJwt(payload, expiresInSeconds = 60 * 60 * 24 * 30) {
  const header = { alg: "HS256", typ: "JWT" };
  const nowSec = Math.floor(Date.now() / 1000);
  const body = {
    ...payload,
    iat: nowSec,
    exp: nowSec + Math.max(60, Number(expiresInSeconds) || 60)
  };

  const headerEncoded = base64UrlEncode(JSON.stringify(header));
  const bodyEncoded = base64UrlEncode(JSON.stringify(body));
  const signingInput = `${headerEncoded}.${bodyEncoded}`;
  const signature = crypto
    .createHmac("sha256", secret())
    .update(signingInput)
    .digest("base64url");
  return `${signingInput}.${signature}`;
}

export function verifyJwt(token) {
  const parts = String(token || "").split(".");
  if (parts.length !== 3) return null;
  const [headerEncoded, bodyEncoded, signature] = parts;
  const signingInput = `${headerEncoded}.${bodyEncoded}`;
  const expected = crypto
    .createHmac("sha256", secret())
    .update(signingInput)
    .digest("base64url");
  if (signature !== expected) return null;

  const payload = JSON.parse(base64UrlDecode(bodyEncoded));
  const nowSec = Math.floor(Date.now() / 1000);
  if (typeof payload.exp !== "number" || payload.exp < nowSec) return null;
  return payload;
}
