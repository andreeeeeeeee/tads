import { NextFunction, Request, Response } from 'express';
import { insertLog } from '../models/LogModel';

export function auditLog(summaryProvider?: (req: Request, res: Response) => string) {
  return (req: Request, res: Response, next: NextFunction) => {
    if (req.method === 'GET') return next();

    const userId = req.session?.user?.id || null;
    const method = req.method;
    const endpoint = req.originalUrl;
    let summary = summaryProvider ? summaryProvider(req, res) : '';

    res.on('finish', () => {
      if (!summary)
        summary = `${method} ${endpoint} - status ${res.statusCode}`;

      insertLog({
        user_id: userId,
        method,
        endpoint,
        summary,
      });
    });
    next();
  };
}
