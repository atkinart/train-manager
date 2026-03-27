# Architecture

## 1. Общая идея

Система строится как event-driven pipeline:
- полевые узлы публикуют факты (`events`);
- backend вычисляет решение (`rules`);
- backend публикует управленческие команды (`commands`);
- полевые узлы публикуют подтверждение состояния (`state`).

## 2. Роли компонентов

- **Arduino Node**: работа с RFID/servo, heartbeat, MQTT I/O.
- **Mosquitto**: транспорт событий/команд.
- **Spring Boot Backend**:
  - нормализация сообщений;
  - хранение текущего состояния;
  - применение правил;
  - API + live updates для UI.
- **Web UI**: наблюдение и ручные действия оператора.

## 3. Компонентная диаграмма

```mermaid
flowchart LR
  subgraph Field[Field level]
    A1[Arduino Node]
    R1[MFRC522 Reader]
    S1[Servo Switch]
    A1 --> R1
    A1 --> S1
  end

  subgraph Core[Central level]
    M[(Mosquitto)]
    B[Spring Boot Backend]
    DB[(In-memory MVP\nFuture: PostgreSQL)]
    B --> DB
  end

  subgraph UI[UI level]
    W[Web UI]
  end

  A1 <-- MQTT --> M
  B <-- MQTT --> M
  W <-- REST/SSE --> B
```

## 4. Sequence: поезд прошёл reader -> переключилась стрелка

```mermaid
sequenceDiagram
  participant T as Train(Tag)
  participant N as Arduino Node
  participant MQ as Mosquitto
  participant BE as Backend
  participant UI as Web UI

  T->>N: RFID tag detected
  N->>MQ: publish rfid.detected
  MQ->>BE: deliver event
  BE->>BE: apply rule
  BE->>MQ: publish switch.command
  MQ->>N: deliver command
  N->>N: servo move
  N->>MQ: publish switch.state
  MQ->>BE: deliver state ack
  BE->>UI: SSE state update
```

## 5. Deployment diagram

```mermaid
flowchart TB
  subgraph Pi[Raspberry Pi / mini PC]
    M[mosquitto container]
    B[backend container]
    F[frontend container]
  end

  subgraph Rail[Railway Layout]
    N1[Arduino node-1]
    N2[Arduino node-2 future]
  end

  Laptop[Operator browser]

  N1 <-- Wi-Fi/Ethernet --> M
  N2 <-- Wi-Fi/Ethernet --> M
  B <-- local docker net --> M
  F <-- HTTP --> B
  Laptop --> F
```

## 6. Границы ответственности

### Arduino
- Debounce/anti-duplicate RFID чтений.
- Исполнение servo-команды.
- Низкоуровневая диагностика (RSSI-like данные, uptime, errors).

### Backend
- Каноническая модель layout и устройств.
- Rule evaluation (stateless/stateful).
- Источник истины по текущему состоянию.
- API для UI и внешних интеграций.

### UI
- Визуализация состояния.
- Журнал событий.
- Manual override (подтверждённые команды).

## 7. Масштабирование

- Topic naming включает `nodeId`, `readerId`, `switchId`.
- Node горизонтально добавляются без изменения протокола.
- Rule engine отделён от транспорта, можно заменить реализацию.
- In-memory storage в MVP заменяется на БД без изменения API контрактов.
