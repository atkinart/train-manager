# Rule engine

## Цель

Преобразовывать RFID events в команды стрелок безопасно и предсказуемо.

## MVP rule pattern

1. Вход: `brio/v1/nodes/{nodeId}/events/rfid.detected`.
2. Lookup правил по `(nodeId, readerId, tagUid)`.
3. Выход: `brio/v1/nodes/{nodeId}/commands/switch.set`.
4. Ожидание подтверждения по `brio/v1/nodes/{nodeId}/state/switch`.

## Fail-safe

- timeout на подтверждение,
- ограничение частоты команд,
- идемпотентность через `commandId`.

## Platform assumption

- MVP runtime: Pico 2 W / Pico 2 WH через USB Serial bridge на Raspberry Pi.
- Future runtime: Pico 2 W direct MQTT over Wi‑Fi (без изменения схемы topics/payload).
