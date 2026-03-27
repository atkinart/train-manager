# Infra

Локальный запуск через Docker Compose.

## Команды

```bash
cd infra
cp .env.example .env
docker compose up -d --build
docker compose ps
docker compose logs -f mosquitto backend frontend
```

## Порты
- mosquitto mqtt: `1883`
- mosquitto websocket: `9001`
- backend: `8080`
- frontend: `5173`

## Проверка broker

```bash
mosquitto_pub -h localhost -t brio/v1/test -m '{"ok":true}'
mosquitto_sub -h localhost -t 'brio/v1/#' -v
```
