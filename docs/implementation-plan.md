# Implementation plan (Pico 2 W primary)

## Phase 1 — MVP node bring-up

1. Собрать hardware:
   - Pico 2 W/WH,
   - 1x MFRC522,
   - 2x servo,
   - external 5V PSU для servo,
   - USB кабель Pico ↔ Raspberry Pi.
2. Проверить питание и COMMON GND.
3. Прошить firmware skeleton из `firmware/pico2w/`.
4. Проверить serial heartbeat.
5. Проверить RFID event и anti-duplicate окно.
6. Проверить команды на servo через serial/MQTT bridge.

## Phase 2 — Hub integration

1. Поднять Mosquitto на Raspberry Pi.
2. Запустить bridge process:
   - вход: USB serial от Pico,
   - выход: MQTT events/heartbeat/state,
   - вход: MQTT command,
   - выход: serial command.
3. Интегрировать backend и UI.

## Phase 3 — Reliability

- watchdog/таймауты,
- retry и idempotency команд,
- улучшение диагностики.

## Phase 4 — Future expansion

- Wi‑Fi MQTT direct mode для Pico 2 W,
- PCA9685 для 4+ servo,
- multi-node orchestration,
- расширенный rule engine.

## Development mode choices

### MVP recommended

- Arduino IDE + Arduino-Pico core
- Быстрый старт для новичка
- Минимальная сложность toolchain

### Future mature option

- Official Pico C/C++ SDK
- Более строгий контроль low-level логики и производительности
