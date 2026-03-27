# Rule Engine

## Цель

Преобразовать входные события в команды управления стрелками.

Rule Engine не зависит от того, как node подключён (USB Serial bridge или direct Wi‑Fi MQTT).

## Модель правила (JSON DSL)

```json
{
  "id": "rule-route-a",
  "enabled": true,
  "when": {
    "eventType": "RFID_DETECTED",
    "readerId": "reader-a",
    "tagUidIn": ["04AABBCCDD", "0400112233"]
  },
  "then": [
    {
      "action": "SET_SWITCH",
      "switchId": "sw-1",
      "targetState": "DIVERGE"
    }
  ],
  "cooldownMs": 1000,
  "priority": 100
}
```

## Принципы принятия решения

1. Фильтр правил по `eventType`.
2. Проверка условий (`readerId`, `tagUid`, future: block occupancy).
3. Сортировка по `priority`.
4. Применение `cooldownMs`.
5. Публикация команд в MQTT (`brio/v1/nodes/{nodeId}/commands/switch.set`).

## Базовое правило MVP

- Если `reader-a` видит тег `04AABBCCDD`, то `sw-1` -> `DIVERGE`.

## Эволюция

- Поддержка цепочек условий (`AND/OR`).
- Поддержка временных окон и состояния участков.
- Конфликт-резолвер для нескольких правил на одну стрелку.
- Rule simulation mode перед записью в production ruleset.
