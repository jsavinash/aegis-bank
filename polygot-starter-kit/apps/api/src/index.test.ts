import { describe, expect, it } from 'vitest';
import { createApp } from './index';

describe('BFF api', () => {
  it('health', async () => {
    const res = await createApp().request('/health');
    expect(res.status).toBe(200);
    expect(await res.json()).toEqual({ ok: true });
  });

  it('opens account, deposits, and aggregates summary', async () => {
    const app = createApp();
    const open = await app.request('/accounts', {
      method: 'POST',
      body: JSON.stringify({ customerId: 'c1', type: 'CHECKING', currency: 'usd' }),
      headers: { 'content-type': 'application/json' },
    });
    expect(open.status).toBe(201);
    const account = (await open.json()) as { id: string; currency: string };
    expect(account.currency).toBe('USD');

    const dep = await app.request(`/accounts/${account.id}/deposits`, {
      method: 'POST',
      body: JSON.stringify({ amount: 100, idempotencyKey: 'd1' }),
      headers: { 'content-type': 'application/json' },
    });
    expect(((await dep.json()) as { balance: number }).balance).toBe(100);

    const summary = await app.request('/summary/c1');
    const body = (await summary.json()) as { totalByCurrency: Record<string, number> };
    expect(body.totalByCurrency.USD).toBe(100);
  });

  it('payment initiation is idempotent', async () => {
    const app = createApp();
    const body = JSON.stringify({
      idempotencyKey: 'k1',
      fromAccount: 'a1',
      toAccount: 'a2',
      amount: 10,
      currency: 'USD',
      type: 'DOMESTIC',
    });
    const first = await app.request('/payments', {
      method: 'POST',
      body,
      headers: { 'content-type': 'application/json' },
    });
    const second = await app.request('/payments', {
      method: 'POST',
      body,
      headers: { 'content-type': 'application/json' },
    });
    const a = (await first.json()) as { id: string };
    const b = (await second.json()) as { id: string };
    expect(a.id).toBe(b.id);
  });

  it('unknown account is 404', async () => {
    const res = await createApp().request('/accounts/nope');
    expect(res.status).toBe(404);
  });
});