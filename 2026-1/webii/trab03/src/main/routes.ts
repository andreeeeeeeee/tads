import { Router } from "express";

import { adaptAuthenticatedHandler } from "../shared/http/adaptAuthenticatedHandler.js";
import type { makeDependencies } from "./container.js";

type AppDependencies = ReturnType<typeof makeDependencies>;

export const createRoutes = (dependencies: AppDependencies): Router => {
  const router = Router();

  router.get("/health", (_request, response) => {
    return response.status(200).json({ status: "ok" });
  });

  router.post("/auth/register", dependencies.registerUserController.handle);
  router.post("/auth/login", dependencies.loginController.handle);

  router.use(dependencies.ensureAuthenticatedMiddleware.handle);

  router.post("/categories", adaptAuthenticatedHandler(dependencies.createCategoryController.handle));
  router.get("/categories", adaptAuthenticatedHandler(dependencies.listCategoriesController.handle));

  router.post("/transactions", adaptAuthenticatedHandler(dependencies.createTransactionController.handle));
  router.get("/transactions", adaptAuthenticatedHandler(dependencies.listTransactionsController.handle));
  router.patch(
    "/transactions/:id/pay",
    adaptAuthenticatedHandler(dependencies.markExpenseAsPaidController.handle)
  );

  router.get(
    "/reports/monthly-balance",
    adaptAuthenticatedHandler(dependencies.getMonthlyBalanceController.handle)
  );
  router.get(
    "/reports/category-summary",
    adaptAuthenticatedHandler(dependencies.getCategorySummaryController.handle)
  );

  return router;
};
