package com.aegis.accounts.command;

/** Command to change account status (freeze/unfreeze/close). */
public record StatusChange(String idempotencyKey) {}