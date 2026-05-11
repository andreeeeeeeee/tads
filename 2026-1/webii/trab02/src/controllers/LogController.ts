import { Request, Response } from 'express';
import { getAllLogs } from '../models/LogModel';

export function getLogs(req: Request, res: Response) {
  if (!req.session?.user || req.session.user.role !== 'admin')
    return res.status(403).send('Acesso restrito a administradores.');

  try {
    const logs = getAllLogs();
    res.render('logs', { logs });
  } catch (err) {
    res.status(500).send('Erro ao buscar logs.');
  }
}
