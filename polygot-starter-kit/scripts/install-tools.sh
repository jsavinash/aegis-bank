#!/usr/bin/env bash
# Idempotent toolchain installer (Node/corepack, uv, SDKMAN+JDK). Non-fatal.
set -uo pipefail
echo "== checking toolchains =="
if command -v node >/dev/null 2>&1 && command -v corepack >/dev/null 2>&1; then
  corepack enable || true
  echo "pnpm via corepack: $(pnpm --version 2>/dev/null || echo 'pending')"
else
  echo "Node 20 not found — install Node 20 first (https://nodejs.org)."
fi
if ! command -v uv >/dev/null 2>&1; then
  echo "installing uv..."
  curl -LsSf https://astral.sh/uv/install.sh | sh || true
fi
command -v uv >/dev/null 2>&1 && echo "uv: $(uv --version)" || echo "uv not found"
if [ ! -f "$HOME/.sdkman/bin/sdkman-init.sh" ]; then
  echo "installing SDKMAN..."
  curl -s "https://get.sdkman.io" | bash || true
fi
if [ -f "$HOME/.sdkman/bin/sdkman-init.sh" ]; then
  # shellcheck disable=SC1090
  source "$HOME/.sdkman/bin/sdkman-init.sh"
  ( cd "$(dirname "$0")/.." && sdk env install ) || true
  java -version 2>&1 | head -1 || true
else
  echo "SDKMAN not found — install JDK 21 manually or via SDKMAN."
fi
echo "== done =="
