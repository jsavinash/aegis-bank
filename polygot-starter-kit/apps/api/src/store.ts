export type Account = {
  id: string;
  customerId: string;
  type: string;
  currency: string;
  balance: number;
  status: string;
};

export type Payment = {
  id: string;
  idempotencyKey: string;
  fromAccount: string;
  toAccount: string;
  amount: number;
  currency: string;
  type: string;
  status: string;
};

export type Store = ReturnType<typeof createStore>;

let counter = 0;
const nextId = (prefix: string) => `${prefix}-${++counter}`;

export function createStore() {
  const accounts = new Map<string, Account>();
  const payments = new Map<string, Payment>();
  const idempotency = new Map<string, string>();

  return {
    openAccount(customerId: string, type: string, currency: string): Account {
      const account: Account = {
        id: nextId('acct'),
        customerId,
        type,
        currency: currency.toUpperCase(),
        balance: 0,
        status: 'ACTIVE',
      };
      accounts.set(account.id, account);
      return account;
    },
    getAccount(id: string): Account | undefined {
      return accounts.get(id);
    },
    listAccounts(): Account[] {
      return [...accounts.values()];
    },
    deposit(id: string, amount: number, idempotencyKey?: string): Account | undefined {
      const account = accounts.get(id);
      if (!account) return undefined;
      if (idempotencyKey && idempotency.has(idempotencyKey)) return account;
      account.balance = Math.round((account.balance + amount) * 100) / 100;
      if (idempotencyKey) idempotency.set(idempotencyKey, account.id);
      return account;
    },
    initiatePayment(input: Omit<Payment, 'id' | 'status'>): Payment {
      const existingId = idempotency.get(input.idempotencyKey);
      if (existingId) {
        const existing = payments.get(existingId);
        if (existing) return existing;
      }
      const payment: Payment = { ...input, id: nextId('pay'), status: 'PENDING' };
      payments.set(payment.id, payment);
      idempotency.set(payment.idempotencyKey, payment.id);
      return payment;
    },
    getPayment(id: string): Payment | undefined {
      return payments.get(id);
    },
  };
}