# MQTT Topics

## Naming convention

Префикс: `brio/v1`

Формат:
- события от node: `brio/v1/nodes/{nodeId}/events/...`
- state от node: `brio/v1/nodes/{nodeId}/state/...`
- команды к node: `brio/v1/nodes/{nodeId}/commands/...`
- backend/system: `brio/v1/system/...`

## MVP transport clarification

- В MVP Pico 2 W не обязан говорить с MQTT напрямую.
- Pico 2 W общается с Raspberry Pi по USB Serial.
- Serial bridge на Raspberry Pi конвертирует Serial сообщения Pico в MQTT topics ниже.
- Поэтому topic contract уже финальный, даже если transport в MVP гибридный.

## Topic tree

- `brio/v1/nodes/{nodeId}/heartbeat`
- `brio/v1/nodes/{nodeId}/events/rfid.detected`
- `brio/v1/nodes/{nodeId}/commands/switch.set`
- `brio/v1/nodes/{nodeId}/state/switch`
- `brio/v1/system/layout/state`
- `brio/v1/system/events`

## QoS / retained / LWT

- heartbeat: QoS 1, retained=false
- rfid events: QoS 1, retained=false
- commands: QoS 1, retained=false
- switch state: QoS 1, retained=true
- LWT (future Wi‑Fi mode): `brio/v1/nodes/{nodeId}/status` payload `OFFLINE`

## Примеры payload JSON

### Heartbeat
```json
{
  "nodeId": "node-1",
  "status": "ONLINE",
  "transport": "USB_SERIAL",
  "uptimeSec": 1234,
  "ts": "2026-03-27T12:00:00Z"
}
```

### RFID event
```json
{
  "eventId": "evt-001",
  "nodeId": "node-1",
  "readerId": "reader-a",
  "tagUid": "04AABBCCDD",
  "ts": "2026-03-27T12:00:01Z"
}
```

### Switch command
```json
{
  "commandId": "cmd-001",
  "switchId": "sw-1",
  "targetState": "DIVERGE",
  "reason": "rule:route-a",
  "ts": "2026-03-27T12:00:01Z"
}
```

### Switch state ack
```json
{
  "nodeId": "node-1",
  "switchId": "sw-1",
  "state": "DIVERGE",
  "source": "COMMAND",
  "ts": "2026-03-27T12:00:02Z"
}
```

## MQTT interaction diagram

```mermaid
flowchart LR
  Pico[Pico 2 W Node] <-->|USB Serial| Bridge[Serial Bridge on Raspberry Pi]
  Bridge -->|events/heartbeat/state| Broker[(Mosquitto)]
  Backend[Spring Backend] -->|commands| Broker
  Broker --> Backend
  Broker --> Bridge
```

## Future mode (direct Wi‑Fi MQTT)

При переходе на Wi‑Fi MQTT direct:
- topic tree не меняется;
- payload format не меняется;
- serial bridge больше не нужен для этого node.
