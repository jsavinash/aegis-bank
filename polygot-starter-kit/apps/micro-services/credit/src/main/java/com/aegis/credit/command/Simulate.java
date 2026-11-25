package com.aegis.credit.command;

/** Command for the what-if simulator: apply {@code delta} to {@code baseScore}. */
public record Simulate(int baseScore, int delta) {}