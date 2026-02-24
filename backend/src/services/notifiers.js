import twilio from "twilio";
import sgMail from "@sendgrid/mail";
import admin from "firebase-admin";

let twilioClient = null;
let firebaseReady = false;

function initFirebase() {
  if (firebaseReady) return;
  const raw = process.env.FIREBASE_SERVICE_ACCOUNT_JSON;
  if (!raw) return;

  try {
    const creds = JSON.parse(raw);
    admin.initializeApp({
      credential: admin.credential.cert(creds)
    });
    firebaseReady = true;
  } catch {
    firebaseReady = false;
  }
}

function getTwilioClient() {
  if (twilioClient) return twilioClient;
  const sid = process.env.TWILIO_ACCOUNT_SID;
  const token = process.env.TWILIO_AUTH_TOKEN;
  if (!sid || !token) return null;
  twilioClient = twilio(sid, token);
  return twilioClient;
}

function initSendGrid() {
  const key = process.env.SENDGRID_API_KEY;
  if (!key) return false;
  sgMail.setApiKey(key);
  return true;
}

export async function sendSmsAlert(to, body) {
  const client = getTwilioClient();
  const from = process.env.TWILIO_FROM_NUMBER;
  if (!client || !from || !to) return { skipped: true };

  await client.messages.create({ to, from, body });
  return { delivered: true };
}

export async function sendEmailAlert(to, subject, text) {
  if (!to) return { skipped: true };
  const ok = initSendGrid();
  const from = process.env.SENDGRID_FROM_EMAIL;
  if (!ok || !from) return { skipped: true };

  await sgMail.send({
    to,
    from,
    subject,
    text
  });
  return { delivered: true };
}

export async function sendPushAlert(token, title, body, data = {}) {
  if (!token) return { skipped: true };
  initFirebase();
  if (!firebaseReady) return { skipped: true };

  await admin.messaging().send({
    token,
    notification: { title, body },
    data
  });
  return { delivered: true };
}
