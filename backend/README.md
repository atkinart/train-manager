# Backend (Spring Boot)

Skeleton backend для управления состоянием макета и правилами.

## Модули/слои
- `mqtt/` — MQTT config, inbound handler, command publisher.
- `store/` — in-memory state store для MVP.
- `rule/` — базовый rule engine.
- `application/` — orchestrating services.
- `api/` — REST/SSE endpoints.
- `domain/` — модель сущностей.

## Запуск локально

```bash
cd backend
./gradlew bootRun
```

или через Docker Compose из `infra/`.

## Важные endpoint

- `GET /api/v1/state`
- `POST /api/v1/switches/manual`
- `GET /api/v1/events/stream`
