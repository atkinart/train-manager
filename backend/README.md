# Backend (Spring Boot)

Skeleton backend для управления состоянием BRIO layout и правилами.

## Canonical contracts

- MQTT topics и payload naming: `docs/mqtt-topics.md`
- Entity naming: `nodeId`, `readerId`, `switchId`, `trainId`
- Primary platform в проекте: Pico 2 W / Pico 2 WH (Arduino path — legacy)

## Модули/слои

- `mqtt/` — MQTT config, inbound handler, command publisher.
- `store/` — in-memory state store для MVP.
- `rule/` — базовый rule engine.
- `application/` — orchestration services.
- `api/` — REST/SSE endpoints.
- `domain/` — модель сущностей.

## Запуск локально

```bash
cd backend
./gradlew bootRun
```

Или через Docker Compose из `infra/`.

## Важные endpoint

- `GET /api/v1/state`
- `POST /api/v1/switches/manual`
- `GET /api/v1/events/stream`
