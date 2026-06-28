import jwt from "jsonwebtoken";

import { InvalidTokenError } from "../../application/errors/InvalidTokenError.js";
import type { AuthTokenPayload, TokenService } from "../../application/services/TokenService.js";

const JWT_SECRET = process.env.JWT_SECRET ?? "dev-secret";

export class JwtTokenService implements TokenService {
  public async sign(payload: AuthTokenPayload): Promise<string> {
    return jwt.sign({ userId: payload.userId }, JWT_SECRET, { expiresIn: "8h" });
  }

  public async verify(token: string): Promise<AuthTokenPayload> {
    try {
      const decoded = jwt.verify(token, JWT_SECRET) as { userId: string };

      return { userId: decoded.userId };
    } catch {
      throw new InvalidTokenError();
    }
  }
}
