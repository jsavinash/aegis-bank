# Aegis Polyglot Starter Kit

Nx monorepo skeleton per `aegis-documentation/04_engineering_and_code/01_codebase_organization_and_standards/01_repository_and_folder_structure.md`.

Layouts follow:
- Java: https://github.com/jsavinash/java-starter-kit
- Python: https://github.com/jsavinash/python-starter-kit
- MCP: https://github.com/jsavinash/mcp-starter-kit
- TypeScript: https://github.com/jsavinash/javascript-starter-kit

## Quick start
```bash
npx nx@latest init
bun install
uv sync --all-packages
./gradlew build
bash scripts/install-hooks.sh
nx graph
```
