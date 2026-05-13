import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import { getAllLogs } from '../models/LogModel';

function getLogs(_req: Request, res: Response) {
  try {
    const logs = getAllLogs();
    res.render('logs', { logs });
  } catch (err) {
    res.status(500).send('Erro ao buscar logs.');
  }
}

const router = Router();

router.get('/logs', requireAuth, requireRole('admin'), getLogs);

export default router;
