# Git Workflow and Code Review Guide

## Branching

- **`main`** protected; requires review + green CI.
- **Short-lived feature branches** (`feat/`, `fix/`, `chore/`).
- **Trunk-based** with feature flags for incomplete work.

## Commits

- **Conventional Commits** (`feat:`, `fix:`, `docs:`, `refactor:`, `test:`).
- **Atomic** commits; no secrets.

## Pull requests

- **Small PRs** (<400 lines); one concern.
- **Description** — what/why/how/tested.
- **Reviewers** — 1 for normal, 2 for Tier 0 / AI / security.
- **CI gates** — lint, type, test, contract, eval, security, a11y.

## Code review

- **Correctness** — does it do what it claims?
- **Security** — injection, secrets, least privilege.
- **AI** — grounding, guardrails, HITL, eval coverage.
- **Accessibility** — WCAG compliance.
- **Tests** — regression coverage.
- **Docs** — ADR updated if architecture changes.

## Merge

- **Squash** to main; delete branch.
- **Changelog** auto-generated from commits.

## AI-assisted development

- **AI coding agents** (Claude Code/Cursor) with `AGENTS.md` conventions.
- **Humans review** all AI-generated code; no auto-merge of Tier 0.