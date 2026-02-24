import express from "express";
import { z } from "zod";
import { createUser, getUserByEmail } from "../store/memoryStore.js";
import { hashPassword, verifyPassword } from "../utils/passwords.js";
import { signJwt } from "../utils/jwt.js";
import { requireUserJwt } from "../utils/auth.js";

const router = express.Router();

function publicUser(user) {
  return {
    id: user.id,
    email: user.email,
    display_name: user.displayName,
    created_at: user.createdAt
  };
}

router.post("/register", (req, res) => {
  const schema = z.object({
    email: z.string().email(),
    password: z.string().min(8),
    display_name: z.string().optional().default("")
  });
  const parsed = schema.safeParse(req.body);
  if (!parsed.success) return res.status(400).json({ error: "invalid_payload" });

  const email = parsed.data.email.trim().toLowerCase();
  const { salt, hash } = hashPassword(parsed.data.password);
  const created = createUser({
    email,
    passwordHash: hash,
    passwordSalt: salt,
    displayName: parsed.data.display_name.trim()
  });
  if (created.error === "email_in_use") return res.status(409).json({ error: "email_in_use" });
  if (!created.user) return res.status(400).json({ error: "create_failed" });

  const token = signJwt({ sub: created.user.id, email: created.user.email });
  return res.status(201).json({ ok: true, token, user: publicUser(created.user) });
});

router.post("/login", (req, res) => {
  const schema = z.object({
    email: z.string().email(),
    password: z.string().min(1)
  });
  const parsed = schema.safeParse(req.body);
  if (!parsed.success) return res.status(400).json({ error: "invalid_payload" });

  const user = getUserByEmail(parsed.data.email.trim().toLowerCase());
  if (!user) return res.status(401).json({ error: "invalid_credentials" });
  if (!verifyPassword(parsed.data.password, user.passwordSalt, user.passwordHash)) {
    return res.status(401).json({ error: "invalid_credentials" });
  }

  const token = signJwt({ sub: user.id, email: user.email });
  return res.json({ ok: true, token, user: publicUser(user) });
});

router.post("/me", requireUserJwt, (req, res) => {
  const user = req.user;
  return res.json({
    ok: true,
    user: {
      id: user.id,
      email: user.email,
      display_name: user.displayName
    }
  });
});

export default router;
