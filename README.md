# BRIO Train Manager

Проект автоматизации BRIO-совместимого макета железной дороги с RFID, **Raspberry Pi Pico 2 W / Pico 2 WH** как контроллером узла, MQTT, Spring Boot и web UI.

> Статус: **foundation/skeleton**. Репозиторий обновлён: базовая целевая платформа узла мигрирована с Arduino-плана на Pico 2 W.

## Цель

Система должна:
- определять, какой поезд (RFID tag) прошёл через какую точку (reader);
- применять правила маршрутизации и переключать стрелки;
- публиковать состояние и события в UI;
- масштабироваться на большее число узлов, стрелок и исполнительных устройств.

## Архитектурный overview

Три уровня:
1. **Полевой уровень (MVP)**: Pico 2 W node + MFRC522 + 2 servo, связь с Raspberry Pi по **USB Serial**.
2. **Центральный уровень**: Mosquitto + Spring Boot backend (state, rules, commands, API/SSE).
3. **UI**: web-панель статуса узлов, readers, стрелок и журнала событий.

Основной event-flow (MVP):
1. Reader считывает RFID UID на Pico 2 W.
2. Pico отправляет событие по USB Serial в Raspberry Pi bridge.
3. Bridge публикует MQTT событие.
4. Backend применяет правила.
5. Backend публикует MQTT команду.
6. Bridge передаёт команду Pico по USB Serial.
7. Pico двигает servo и отправляет подтверждение состояния.

## Почему Pico 2 W выбран как primary controller

- **3.3V логика**: естественно совместима с MFRC522 без лишней level shifting сложности.
- **Цена и доступность**: удобен для MVP и масштабирования на несколько узлов.
- **Future-ready**: Pico 2 W имеет Wi‑Fi для перехода на прямой MQTT в будущем.
- **Pico 2 W vs Pico 2 WH**:
  - **Pico 2 WH** — быстрее стартовать (header pins уже припаяны).
  - **Pico 2 W** — удобнее для более компактной интеграции.

## Структура репозитория

- `docs/` — архитектура, wiring, питание, MQTT, rules, roadmap, отладка.
- `docs/electrical/` — отдельные beginner-friendly электрические инструкции.
- `infra/` — локальная инфраструктура (docker-compose, mosquitto config).
- `backend/` — Spring Boot skeleton.
- `frontend/` — Vite + React skeleton для панели мониторинга/управления.
- `firmware/pico2w/` — основной firmware skeleton для Pico 2 W (Arduino-style).
- `arduino/` — legacy/alternative path (не primary).
- `examples/` — примерные JSON payload/registry/layout/rules.

## Quick start (локально)

### 1) Инфраструктура

```bash
cd infra
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

### 3) MVP hardware path

1. Соберите wiring по `docs/wiring-guide.md`.
2. Прошейте Pico 2 W по `firmware/pico2w/README.md`.
3. Подключите Pico 2 W к Raspberry Pi по USB.
4. Запустите serial↔MQTT bridge на Raspberry Pi.
5. Проверьте heartbeat в MQTT:

```bash
mosquitto_sub -h localhost -t 'brio/v1/#' -v
```

## MVP scope (реально в этом репо)

Реализовано как skeleton:
- согласованная модель сущностей и topic tree;
- backend каркас: MQTT inbound/outbound, in-memory store, базовое правило, REST + SSE;
- frontend каркас: списки устройств, состояние, live event log, manual switch action;
- firmware каркас Pico 2 W с anti-duplicate RFID, heartbeat и command handler заготовкой;
- подробная практическая документация по wiring/питанию/отладке с акцентом на 3.3V logic + external 5V servo power.

Не реализовано полностью (нужно доделывать):
- production-grade rule engine;
- подтверждённый end-to-end с реальным железом;
- авторизация/безопасность;
- устойчивое хранение состояния (БД);
- production serial bridge service.

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
- [Electrical docs index](docs/electrical/README.md)

## Migration from Arduino plan to Pico 2 W

Что изменено:
- Pico 2 W/Pico 2 WH назначены **основной** платформой контроллера узла.
- MVP канал связи изменён на **USB Serial Pico ↔ Raspberry Pi**.
- Обновлены wiring, power и safety требования под **3.3V GPIO** Pico.
- Добавлен новый основной firmware path: `firmware/pico2w/`.
- Arduino path сохранён как **legacy/alternative**, чтобы не ломать ранние наработки.

Что это значит для вас:
- Для быстрого старта берите **Pico 2 WH**.
- Для более компактной интеграции берите **Pico 2 W**.
- Если у вас уже собран Arduino-макет, можно продолжать как compatibility path, но основной поток документации теперь Pico-centric.

## Next steps

1. Купить минимальный комплект из `docs/bom.md`.
2. Выполнить по шагам `docs/wiring-guide.md` и `docs/debugging.md`.
3. Подтвердить стабильные heartbeat + RFID event через Pico 2 W и USB Serial bridge.
4. Подключить backend к broker и проверить автоматическое переключение 1 стрелки по базовому правилу.
5. Добавить вторую стрелку, обновить `examples/layout.json` и device registry.
