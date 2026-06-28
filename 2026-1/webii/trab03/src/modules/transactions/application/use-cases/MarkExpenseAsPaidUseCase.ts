import { ExpenseAlreadyPaidError } from "../errors/ExpenseAlreadyPaidError.js";
import { TransactionNotFoundError } from "../errors/TransactionNotFoundError.js";
import type { Transaction } from "../../domain/entities/Transaction.js";
import type { TransactionRepository } from "../../domain/repositories/TransactionRepository.js";

export type MarkExpenseAsPaidInput = {
  userId: string;
  transactionId: string;
  paidAt?: Date;
};

export class MarkExpenseAsPaidUseCase {
  constructor(private readonly transactionRepository: TransactionRepository) {}

  public async execute(input: MarkExpenseAsPaidInput): Promise<Transaction> {
    const transaction = await this.transactionRepository.findById(input.transactionId);

    if (!transaction || transaction.userId !== input.userId) {
      throw new TransactionNotFoundError();
    }

    if (transaction.status === "paid") {
      throw new ExpenseAlreadyPaidError();
    }

    const paidTransaction = transaction.markAsPaid(input.paidAt);

    await this.transactionRepository.update(paidTransaction);

    return paidTransaction;
  }
}
