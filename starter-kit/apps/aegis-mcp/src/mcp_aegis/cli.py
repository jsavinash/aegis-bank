import os
from .server import build_server

def main() -> None:
    s = build_server()
    transport = os.getenv('MCP_TRANSPORT','stdio')
    s.run(transport=transport)
