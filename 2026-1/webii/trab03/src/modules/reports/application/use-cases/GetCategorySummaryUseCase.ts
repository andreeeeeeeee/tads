import type { CategoryRepository } from "../../../categories/domain/repositories/CategoryRepository.js";
import type { TransactionRepository } from "../../../transactions/domain/repositories/TransactionRepository.js";

export type GetCategorySummaryInput = {
  userId: string;
  month: number;
  year: number;
};

export type CategorySummaryItem = {
  categoryId: string;
  categoryName: string;
  total: number;
};

export class GetCategorySummaryUseCase {
  constructor(
    private readonly transactionRepository: TransactionRepository,
    private readonly categoryRepository: CategoryRepository
  ) {}

  public async execute(input: GetCategorySummaryInput): Promise<CategorySummaryItem[]> {
    const transactions = await this.transactionRepository.listByUserId(input.userId, {
      month: input.month,
      year: input.year
    });

    const totalsByCategory = new Map<string, number>();

    for (const transaction of transactions) {
      const currentTotal = totalsByCategory.get(transaction.categoryId) ?? 0;
      totalsByCategory.set(transaction.categoryId, currentTotal + transaction.amount);
    }

    const summary: CategorySummaryItem[] = [];

    for (const [categoryId, total] of totalsByCategory) {
      const category = await this.categoryRepository.findById(categoryId);

      summary.push({
        categoryId,
        categoryName: category?.name ?? "Unknown",
        total
      });
    }

    return summary.sort((a, b) => a.categoryName.localeCompare(b.categoryName));
  }
}
