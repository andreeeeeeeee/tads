import type { User } from "../../domain/entities/User.js";
import type { UserRepository } from "../../domain/repositories/UserRepository.js";

export class InMemoryUserRepository implements UserRepository {
  private readonly users = new Map<string, User>();

  public async findById(id: string): Promise<User | null> {
    return Array.from(this.users.values()).find((user) => user.id === id) ?? null;
  }

  public async findByEmail(email: string): Promise<User | null> {
    return this.users.get(email) ?? null;
  }

  public async create(user: User): Promise<void> {
    this.users.set(user.email, user);
  }
}
