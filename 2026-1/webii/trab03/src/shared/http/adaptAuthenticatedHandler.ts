import type { RequestHandler, Response } from "express";

import type { AuthenticatedRequest } from "./AuthenticatedRequest.js";

type AuthenticatedHandler = (
  request: AuthenticatedRequest,
  response: Response
) => Promise<Response> | Response | void;

export const adaptAuthenticatedHandler = (handler: AuthenticatedHandler): RequestHandler => {
  return handler as unknown as RequestHandler;
};
