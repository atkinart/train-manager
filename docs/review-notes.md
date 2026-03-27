# Repository review notes (2026-03-27)

Этот файл фиксирует результаты полной ревизии репозитория.

## Найденные несоответствия

1. MQTT schema в `docs/mqtt-topics.md` не совпадала с backend (`brio/node/...` vs `brio/v1/nodes/...`).
2. Naming был неоднородным (`uid` vs `tagUid`, `cmdId` vs `commandId`, `node-01` vs `node-1`).
3. В `examples/device-registry.json` тип узла был `ARDUINO`, что конфликтует с текущей primary платформой Pico 2 W.
4. Switch command не всегда содержал `nodeId` в payload/examples.
5. Для новичка не было единой стартовой страницы с безопасным порядком действий.

## Что исправлено

- Выровнен canonical MQTT topic/payload contract (`docs/mqtt-topics.md`).
- Унифицированы naming conventions в docs (`docs/layout-model.md`, `docs/rule-engine.md`).
- Примеры приведены к Pico 2 W primary пути (`examples/device-registry.json`, `examples/switch-command.json`, `examples/mqtt-payloads.json`).
- Backend `SwitchCommand` расширен полем `nodeId` для согласованности с docs/examples.
- Добавлен beginner-first entry point: `docs/start-here.md`.
- Обновлён root README как единая карта чтения и сборки MVP.

## Что оставлено как legacy

- Папка `arduino/` сохранена как historical/alternative path.
- Документы с Arduino в `docs/electrical/` оставлены только как помеченные legacy notes.

## Следующий ручной шаг

- Реализовать и задокументировать production-ready serial↔MQTT bridge (формат serial сообщений, mapping в MQTT JSON, retry/error policy).
