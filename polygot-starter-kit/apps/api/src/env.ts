import { z } from 'zod';
export const env = z.object({ DATABASE_URL: z.string().url().optional(), REDIS_URL: z.string().optional() }).parse(process.env);
