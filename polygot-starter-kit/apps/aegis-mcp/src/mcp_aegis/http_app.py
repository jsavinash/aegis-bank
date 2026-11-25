from .server import build_server

app = build_server().http_app()
