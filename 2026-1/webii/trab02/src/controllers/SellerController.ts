import { Request, Response } from 'express';
import * as ProductModel from '../models/ProductModel';

export function dashboard(req: Request, res: Response) {
  const produtos = ProductModel.findByUser(req.session.user!.id);
  res.render('seller-dashboard', { produtos });
}
