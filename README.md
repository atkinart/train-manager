# BRIO Train Manager

Проект автоматизации BRIO-совместимого макета железной дороги с RFID, Arduino, MQTT, Spring Boot и web UI.

> Статус: **foundation/skeleton**. Репозиторий подготовлен как практичная база для MVP и дальнейшего роста.

## Цель

Система должна:
- определять, какой поезд (RFID tag) прошёл через какую точку (reader);
- применять правила маршрутизации и переключать стрелки;
- публиковать состояние и события в UI;
- масштабироваться на большее число узлов, стрелок и исполнительных устройств.

## Архитектурный overview

Три уровня:
1. **Полевой уровень**: Arduino node + MFRC522 + servo, события и исполнение команд.
2. **Центральный уровень**: Mosquitto + Spring Boot backend (state, rules, commands, API/SSE).
3. **UI**: web-панель статуса узлов, readers, стрелок и журнала событий.

Основной event-flow:
1. Reader считывает RFID UID.
2. Node публикует MQTT событие.
3. Backend применяет правила.
4. Backend публикует команду на switch.
5. Node исполняет команду и публикует подтверждение состояния.

## Структура репозитория

- `docs/` — архитектура, wiring, питание, MQTT, rules, roadmap, отладка.
- `infra/` — локальная инфраструктура (docker-compose, mosquitto config).
- `backend/` — Spring Boot skeleton.
- `frontend/` — Vite + React skeleton для панели мониторинга/управления.
- `arduino/` — firmware skeleton для node.
- `examples/` — примерные JSON payload/registry/layout/rules.

## Почему выбранные технологии

- **MQTT + Mosquitto**: простой и надёжный event bus для embedded ↔ backend.
- **Spring Boot**: быстрый старт и расширяемая архитектура для domain/state/rules/API.
- **Gradle (Kotlin DSL)**: удобен для multi-module роста и декларативного управления зависимостями.
- **Vite + React**: низкий порог старта, быстрый dev-loop, легко наращивать UI.

## Quick start (локально)

### 1) Инфраструктура

```bash
cd infra
cp .env.example .env
docker compose up -d --build
```

Поднимутся:
- Mosquitto: `1883`, `9001`
- Backend API: `8080`
- Frontend: `5173`

### 2) Проверка

- Backend health: `http://localhost:8080/actuator/health`
- State API: `http://localhost:8080/api/v1/state`
- UI: `http://localhost:5173`

### 3) MQTT smoke test

```bash
mosquitto_pub -h localhost -t brio/v1/nodes/node-1/heartbeat -m '{"nodeId":"node-1","status":"ONLINE","ts":"2026-03-27T12:00:00Z"}'
```

## MVP scope (реально в этом репо)

Реализовано как skeleton:
- согласованная модель сущностей и topic tree;
- backend каркас: MQTT inbound/outbound, in-memory store, базовое правило, REST + SSE;
- frontend каркас: списки устройств, состояние, live event log, manual switch action;
- arduino firmware каркас с anti-duplicate RFID, heartbeat и command handler заготовкой;
- подробная практическая документация по wiring/питанию/отладке.

Не реализовано полностью (нужно доделывать):
- production-grade rule engine;
- подтверждённый end-to-end с реальным железом;
- авторизация/безопасность;
- устойчивое хранение состояния (БД).

## Документация

- [Architecture](docs/architecture.md)
- [Implementation plan](docs/implementation-plan.md)
- [BOM](docs/bom.md)
- [Wiring guide](docs/wiring-guide.md)
- [Power design](docs/power-design.md)
- [MQTT topics](docs/mqtt-topics.md)
- [Rule engine](docs/rule-engine.md)
- [Layout model](docs/layout-model.md)
- [Debugging](docs/debugging.md)
- [Roadmap](docs/roadmap.md)

## Next steps

1. Купить минимальный комплект из `docs/bom.md`.
2. Выполнить по шагам `docs/wiring-guide.md` и `docs/debugging.md`.
3. Прошить один Arduino node и подтвердить стабильный heartbeat + RFID event в MQTT.
4. Подключить backend к broker и проверить автоматическое переключение 1 стрелки по базовому правилу.
5. Добавить второй reader/вторую стрелку, обновить `examples/layout.json` и device registry.
