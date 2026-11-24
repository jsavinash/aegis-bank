# 01 — Project Overview and Context

## Problem

Retail banks face three simultaneous pressures: customers expect neobank-grade digital experiences; fraud and financial crime are escalating; and regulation (PSD2/PSD3, EU AI Act, EAA, AML/KYC) demands explainable, auditable systems. Legacy cores and rule-based controls cannot keep pace — AML false-positive rates exceed 90%, and fraud rules encode yesterday's attacks.

## Opportunity

A greenfield AI-first bank can:
1. Deliver a unified, accessible, mobile-first experience across the full product surface.
2. Use AI as the decision engine for fraud, AML, underwriting, and service — with humans accountable.
3. Turn compliance into a competitive advantage via audit-ready, explainable AI.

## Context

- **Market:** 2026 benchmarks show AI leaders (JPMorgan, DBS, Capital One, CommBank) pulling ahead; laggards run pilot factories.
- **Technology:** the open-source AI stack is production-ready (LangGraph, LlamaIndex, vLLM, Qdrant, NeMo, Langfuse, Ragas).
- **Regulation:** EU AI Act risk tiers, PSD2/PSD3, EAA, GDPR/CCPA, AML/KYC (FATF, FinCEN) all apply.

## Objectives

- Launch a production-grade MVP (accounts, cards, payments, security base) in ~3 months.
- Prove the AI operating model on 2–3 deep use cases (fraud, servicing, AML) before breadth.
- Reach 99.95% availability, <150ms fraud decisions, WCAG 2.2 AA.

## Constraints

- Data residency and regulatory scrutiny (must self-host where required).
- Fair-lending scrutiny on underwriting (sequence late, human-in-loop).
- Core-banking complexity (buy, don't build).

## Success metrics

See `03_business_goals_and_kpis.md`.