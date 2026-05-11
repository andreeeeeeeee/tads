import { Request, Response } from 'express';
import * as UserModel from '../models/UserModel';

export function profile(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('buyer-profile', { user });
}

export function showEdit(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('buyer-edit', { user, error: null });
}

export function update(req: Request, res: Response) {
  const { telefone, endereco, cidade, estado, cep, pagamento } = req.body;
  const result = UserModel.updateBuyerProfile(req.session.user!.id, {
    telefone,
    endereco,
    cidade,
    estado,
    cep,
    pagamento,
  });
  if (result.error) {
    const user = UserModel.findById(req.session.user!.id);
    return res.render('buyer-edit', { user, error: result.error });
  }
  res.redirect('/comprador/perfil');
}
