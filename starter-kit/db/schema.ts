import { pgTable, uuid } from 'drizzle-orm/pg-core';
export const customers = pgTable('customers', { id: uuid('id').primaryKey() });
