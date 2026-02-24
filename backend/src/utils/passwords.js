import crypto from "node:crypto";

const PBKDF2_ROUNDS = 120000;
const KEY_BYTES = 32;
const DIGEST = "sha256";

export function hashPassword(password, salt = crypto.randomBytes(16).toString("hex")) {
  const normalized = String(password || "");
  const hash = crypto
    .pbkdf2Sync(normalized, salt, PBKDF2_ROUNDS, KEY_BYTES, DIGEST)
    .toString("hex");
  return { salt, hash };
}

export function verifyPassword(password, salt, expectedHash) {
  const computed = hashPassword(password, salt).hash;
  const expected = Buffer.from(String(expectedHash || ""), "hex");
  const actual = Buffer.from(computed, "hex");
  if (expected.length !== actual.length) return false;
  return crypto.timingSafeEqual(expected, actual);
}
