#!/usr/bin/env bash
# Java control script (wraps Gradle) — run from repo root.
set -euo pipefail
cd "$(dirname "$0")"
cmd="${1:-help}"
case "$cmd" in
  build)   ./gradlew clean build ;;
  test)    ./gradlew test ;;
  run)     ./gradlew -p apps/micro-services payments:bootRun ;;
  quality) ./gradlew spotlessApply check ;;
  format)  ./gradlew spotlessApply ;;
  docker)  ./gradlew -p apps/micro-services payments:jibDockerBuild ;;
  clean)   ./gradlew clean ;;
  deps)    ./gradlew dependencies ;;
  help|*)  echo "usage: ./java.sh {build|test|run|quality|format|docker|clean|deps}" ;;
esac
