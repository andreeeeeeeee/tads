import { NextFunction, Request, Response } from 'express';

export function requireGuest(req: Request, res: Response, next: NextFunction): void {
  if (req.session.user) {
    res.redirect('/');
    return;
  }
  next();
}
