# Layout model

## Базовые сущности

- **Node**: Pico 2 W контроллер с readers/switches.
- **Reader**: RFID точка входа события.
- **Switch**: стрелка с позициями `STRAIGHT` / `DIVERGE`.
- **Train event**: прохождение RFID метки через reader.

## Пример MVP модели

- 1 node (`node-01`)
- 1 reader (`reader-1`)
- 2 switch (`switch-1`, `switch-2`)

## Future model

- несколько node на Pico 2 W,
- дополнительные readers,
- block sections и signals.
