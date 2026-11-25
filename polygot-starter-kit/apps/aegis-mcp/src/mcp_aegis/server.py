from mcp.server import MCPServer

from .features.tools import register


def build_server() -> MCPServer:
    server = MCPServer('aegis')
    register(server)
    return server