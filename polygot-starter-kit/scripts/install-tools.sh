#!/usr/bin/env bash
# Toolchain installer: fnm (Node) + portable JDK + uv. No brew/SDKMAN required.
set -uo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
TOOLS="$ROOT/.tools"; mkdir -p "$TOOLS"
ARCH="$(uname -m)"; case "$ARCH" in arm64) JDK_ARCH=aarch64;; *) JDK_ARCH=x64;; esac

echo "== Node (fnm) =="
if ! command -v fnm >/dev/null 2>&1; then
  curl -fsSL -o "$TOOLS/fnm.zip" https://github.com/Schniz/fnm/releases/latest/download/fnm-macos.zip || true
  unzip -o "$TOOLS/fnm.zip" -d "$TOOLS/fnm" >/dev/null 2>&1 || true
  chmod +x "$TOOLS/fnm/fnm" 2>/dev/null || true
fi
export PATH="$TOOLS/fnm:$PATH"
if command -v fnm >/dev/null 2>&1; then
  eval "$(fnm env --shell bash)"
  fnm install 20 || true
  fnm use 20 || true
fi
command -v corepack >/dev/null 2>&1 && corepack enable || true
node --version 2>/dev/null || echo "node install failed"

echo "== JDK 21 =="
JDK_HOME=""
if java -version >/dev/null 2>&1; then
  JDK_HOME="$(java -XshowSettings:properties -version 2>&1 | awk -F= '/java.home/{print $2}')"
else
  [ -f "$TOOLS/jdk.tar.gz" ] || curl -fsSL -o "$TOOLS/jdk.tar.gz" "https://api.adoptium.net/v3/binary/latest/21/ga/mac/$JDK_ARCH/jdk/hotspot/normal/eclipse?project=jdk"
  tar -xzf "$TOOLS/jdk.tar.gz" -C "$TOOLS"
  JDK_HOME="$(ls -d "$TOOLS"/jdk-21*/Contents/Home 2>/dev/null | head -1)"
fi
[ -n "$JDK_HOME" ] && "$JDK_HOME/bin/java" -version 2>&1 | head -1 || echo "JDK install failed"

cat > "$TOOLS/env.sh" <<EOF
export PATH="$TOOLS/fnm:\$PATH"
[ -s "$TOOLS/fnm/fnm.env" ] && . "$TOOLS/fnm/fnm.env"
command -v fnm >/dev/null 2>&1 && eval "\$(fnm env --shell bash)"
export JAVA_HOME="$JDK_HOME"
export PATH="\$JAVA_HOME/bin:\$PATH"
EOF
echo "== wrote $TOOLS/env.sh =="
