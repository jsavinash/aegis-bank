import { Hono } from 'hono';
import { createStore, type Store } from './store';

export function createApp(store: Store = createStore()) {
  const app = new Hono();

  app.get('/health', c => c.json({ ok: true }));

  app.post('/accounts', async c => {
    const body = await c.req.json<{ customerId: string; type: string; currency: string }>();
    const account = store.openAccount(body.customerId, body.type, body.currency);
    return c.json(account, 201);
  });

  app.get('/accounts', c => c.json(store.listAccounts()));

  app.get('/accounts/:id', c => {
    const account = store.getAccount(c.req.param('id'));
    return account ? c.json(account) : c.json({ error: 'account_not_found' }, 404);
  });

  app.post('/accounts/:id/deposits', async c => {
    const body = await c.req.json<{ amount: number; idempotencyKey?: string }>();
    const account = store.deposit(c.req.param('id'), body.amount, body.idempotencyKey);
    return account ? c.json(account) : c.json({ error: 'account_not_found' }, 404);
  });

  app.post('/payments', async c => {
    const body = await c.req.json<{
      idempotencyKey: string;
      fromAccount: string;
      toAccount: string;
      amount: number;
      currency: string;
      type: string;
    }>();
    const payment = store.initiatePayment(body);
    return c.json(payment, 201);
  });

  app.get('/payments/:id', c => {
    const payment = store.getPayment(c.req.param('id'));
    return payment ? c.json(payment) : c.json({ error: 'payment_not_found' }, 404);
  });

  // BFF aggregation: shape a customer's accounts + balances for the client.
  app.get('/summary/:customerId', c => {
    const accounts = store.listAccounts().filter(a => a.customerId === c.req.param('customerId'));
    const totalByCurrency: Record<string, number> = {};
    for (const a of accounts) {
      totalByCurrency[a.currency] = (totalByCurrency[a.currency] ?? 0) + a.balance;
    }
    return c.json({ customerId: c.req.param('customerId'), accounts, totalByCurrency });
  });

  return app;
}

const app = createApp();
export default app;