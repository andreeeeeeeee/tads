import { randomBytes, scryptSync, timingSafeEqual } from "node:crypto";

import type { PasswordHasher } from "../../application/services/PasswordHasher.js";

const KEY_LENGTH = 64;

export class ScryptPasswordHasher implements PasswordHasher {
  public async hash(value: string): Promise<string> {
    const salt = randomBytes(16).toString("hex");
    const hash = scryptSync(value, salt, KEY_LENGTH).toString("hex");

    return `${salt}:${hash}`;
  }

  public async compare(value: string, storedHash: string): Promise<boolean> {
    const [salt, hash] = storedHash.split(":");

    if (!salt || !hash) {
      return false;
    }

    const hashBuffer = Buffer.from(hash, "hex");
    const passwordBuffer = scryptSync(value, salt, KEY_LENGTH);

    if (hashBuffer.length !== passwordBuffer.length) {
      return false;
    }

    return timingSafeEqual(hashBuffer, passwordBuffer);
  }
}
