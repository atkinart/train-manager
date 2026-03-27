# Rule engine

## Цель

Преобразовывать RFID события в команды стрелок безопасно и предсказуемо.

## MVP rule pattern

1. Вход: `event/rfid`.
2. Lookup правил по `(nodeId, readerId, uid)`.
3. Выход: команда `cmd/switch/{id}`.
4. Ожидание `state/ack`.

## Fail-safe

- timeout на подтверждение,
- ограничение частоты команд,
- идемпотентность через `cmdId`.

## Platform assumption

Node runtime в MVP — Pico 2 W через USB Serial bridge.
Future runtime — Pico 2 W direct MQTT over Wi‑Fi.
