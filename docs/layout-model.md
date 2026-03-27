# Layout model

## Базовые сущности

- **Node**: Pico 2 W / Pico 2 WH контроллер с readers и switches.
- **Reader**: RFID-точка, которая генерирует `rfid.detected` event.
- **Switch**: стрелка с состояниями `STRAIGHT` / `DIVERGE`.
- **Train**: поезд, связанный с RFID UID через `trainId`.
- **Switch command**: команда `switch.set` с `commandId`, `nodeId`, `switchId` и `targetState`.
- **Switch state**: подтверждение текущего состояния стрелки.

## Naming conventions (canonical)

- `nodeId`: `node-1`, `node-2`
- `readerId`: `reader-a`, `reader-b`
- `switchId`: `sw-1`, `sw-2`
- `trainId`: `train-1`, `train-2`

> Используйте эти имена и форматы во всех docs, examples, backend DTO/domain и UI.

## Пример MVP модели

- 1 node (`node-1`)
- 1 reader (`reader-a`)
- 2 switches (`sw-1`, `sw-2`)

## Future model

- несколько node на Pico 2 W,
- дополнительные readers,
- block sections, signals и расширенные rules.
