# Train Manager (BRIO automation)

Проект автоматизации BRIO-совместимого макета железной дороги с RFID, **Raspberry Pi Pico 2 W / Pico 2 WH**, MQTT, Spring Boot backend и web UI.

> Статус платформы: **Pico 2 W / Pico 2 WH — основной контроллер узла**. Arduino-путь сохранён только как legacy/alternative.

## С чего начать новичку (рекомендуемый маршрут)

1. Прочитайте `docs/start-here.md`.
2. Проверьте инварианты безопасности в `docs/wiring-guide.md` и `docs/power-design.md`.
3. Соберите MVP-подключение по `docs/electrical/wiring-table-mvp.md`.
4. Прошейте `firmware/pico2w/`.
5. Проверьте serial heartbeat и RFID.
6. Поднимите backend/frontend/infra и проверьте MQTT flow по `docs/mqtt-topics.md`.

## Целевая архитектура

1. **Node level (MVP)**: Pico 2 W + MFRC522 + 2 servo.
2. **Transport (MVP)**: USB Serial между Pico 2 W и Raspberry Pi.
3. **Hub level**: Raspberry Pi + Mosquitto + serial↔MQTT bridge.
4. **Application level**: Spring Boot backend + JS web UI.

Future mode:
- direct Wi‑Fi MQTT с Pico 2 W,
- несколько Pico-узлов,
- расширение на PCA9685 для большого числа servo,
- block sections, signals и gates.

## Безопасность электрической части (обязательно)

- Pico GPIO = **только 3.3V logic**.
- **Нельзя** подавать 5V сигнал напрямую на GPIO Pico.
- Servo питаются только от **external 5V power**.
- **COMMON GND обязателен**: Pico GND + servo PSU GND + RFID GND.
- MFRC522 подключается как 3.3V-устройство.
- Минимально безопасный порядок: сначала Pico+RFID, потом servo signal, потом внешнее 5V питание servo.

## Единые термины и naming

- Платформа: `Pico 2 W` / `Pico 2 WH`.
- MQTT: `brio/v1/nodes/{nodeId}/...`.
- Идентификаторы: `nodeId`, `readerId`, `switchId`, `trainId`.
- Событие RFID: `rfid.detected`.
- Команда: `switch.set`.
- Состояние стрелки: `switch state` (topic `state/switch`).

Подробно: `docs/mqtt-topics.md` и `docs/layout-model.md`.

## Структура репозитория

- `docs/` — архитектура, wiring, питание, roadmap, debug, review notes.
- `docs/electrical/` — подробная электрическая документация и диаграммы.
- `firmware/pico2w/` — **основной** firmware skeleton для Pico 2 W / Pico 2 WH.
- `backend/` — Spring Boot skeleton (MQTT ingest + API).
- `frontend/` — web UI skeleton.
- `examples/` — канонические примеры payload/конфигураций.
- `infra/` — docker-compose и Mosquitto config.
- `arduino/` — legacy/alternative path (не primary).

## Что уже есть сейчас

- архитектурные документы,
- electrical/wiring документы и диаграммы,
- backend/frontend skeleton,
- firmware skeleton,
- BOM/shopping list,
- migration-материалы Arduino → Pico 2 W.

## Что дальше (следующие ручные шаги)

1. Реализовать production-ready serial↔MQTT bridge (с валидацией JSON и retry).
2. Дореализовать firmware drivers (реальный MFRC522 + servo calibration + ack).
3. Добавить интеграционные тесты backend на topic/payload schema.
4. Подготовить first hardware bring-up checklist с фото/измерениями.

## Migration from Arduino plan to Pico 2 W

Что изменено:
- Pico 2 W/Pico 2 WH закреплён как primary платформа node-контроллера.
- MVP transport закреплён как USB Serial Pico ↔ Raspberry Pi.
- Документация выровнена под 3.3V logic + external 5V servo power + common ground.

Что осталось legacy:
- `arduino/` сохранён как исторический/альтернативный путь и не является основной веткой развития.
