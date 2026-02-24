import { getUserById } from "../store/memoryStore.js";
import { verifyJwt } from "./jwt.js";

function readBearerToken(req) {
  const header = req.headers.authorization || "";
  return header.startsWith("Bearer ") ? header.slice(7).trim() : "";
}

export function requireBearer(req, res, next) {
  const expected = process.env.API_BEARER_TOKEN?.trim();
  if (!expected) return next();

  const authToken = readBearerToken(req);
  const apiKeyHeader = String(req.headers["x-api-key"] || "").trim();
  const hasExpected =
    (authToken && authToken === expected) ||
    (apiKeyHeader && apiKeyHeader === expected);
  if (!hasExpected) {
    return res.status(401).json({ error: "unauthorized" });
  }
  return next();
}

export function requireUserJwt(req, res, next) {
  const token = readBearerToken(req);
  if (!token) return res.status(401).json({ error: "missing_user_token" });

  const claims = verifyJwt(token);
  if (!claims?.sub) return res.status(401).json({ error: "invalid_user_token" });
  const user = getUserById(String(claims.sub));
  if (!user) return res.status(401).json({ error: "user_not_found" });
  req.user = user;
  return next();
}

export function optionalUserJwt(req, _res, next) {
  const token = readBearerToken(req);
  if (!token) return next();
  const claims = verifyJwt(token);
  if (!claims?.sub) return next();
  const user = getUserById(String(claims.sub));
  if (!user) return next();
  req.user = user;
  return next();
}
