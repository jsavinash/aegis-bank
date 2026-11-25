FROM python:3.12-slim
WORKDIR /app
COPY . .
RUN pip install uv && uv sync --package aegis
CMD ["uv","run","aegis"]
