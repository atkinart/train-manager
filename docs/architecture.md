# Architecture

## 1. Общая идея

Система строится как event-driven pipeline:
- полевые узлы публикуют факты (`events`);
- backend вычисляет решение (`rules`);
- backend публикует управленческие команды (`commands`);
- полевые узлы публикуют подтверждение состояния (`state`).

В этом проекте **primary node controller = Raspberry Pi Pico 2 W / Pico 2 WH**.

## 2. MVP vs Future architecture

### MVP (default)
- Node controller: Pico 2 W / Pico 2 WH.
- Reader: MFRC522 (SPI, 3.3V logic).
- Actuators: 2 servo (external 5V power).
- Transport Pico ↔ Raspberry Pi: **USB Serial**.
- Transport Raspberry Pi ↔ backend: MQTT (Mosquitto).

### Future
- Pico 2 W подключается по Wi‑Fi напрямую к MQTT broker.
- Много узлов Pico.
- PCA9685 для большого числа servo.
- Block sections / gates / signals.

## 3. Роли компонентов

- **Pico 2 W Node**: RFID/servo, heartbeat, anti-duplicate, Serial transport (MVP).
- **Serial Bridge (на Raspberry Pi)**: перевод Serial сообщений Pico в MQTT и обратно.
- **Mosquitto**: транспорт событий/команд.
- **Spring Boot Backend**:
  - нормализация сообщений;
  - хранение текущего состояния;
  - применение правил;
  - API + live updates для UI.
- **Web UI**: наблюдение и ручные действия оператора.

## 4. Компонентная диаграмма (MVP)

```mermaid
flowchart LR
  subgraph Field[Field level]
    P1[Pico 2 W / 2 WH]
    R1[MFRC522 SPI 3.3V]
    S1[Servo #1 PWM]
    S2[Servo #2 PWM]
    PSU[External 5V Servo PSU]
    P1 <-- SPI --> R1
    P1 -->|PWM| S1
    P1 -->|PWM| S2
    PSU --> S1
    PSU --> S2
    P1 --- GND[(COMMON GND)]
    R1 --- GND
    S1 --- GND
    S2 --- GND
  end

  subgraph Pi[Raspberry Pi]
    BR[Serial↔MQTT Bridge]
    M[(Mosquitto)]
    B[Spring Boot Backend]
    DB[(In-memory MVP\nFuture: PostgreSQL)]
    BR <-- MQTT --> M
    B <-- MQTT --> M
    B --> DB
  end

  subgraph UI[UI level]
    W[Web UI]
  end

  P1 <-- USB Serial --> BR
  W <-- REST/SSE --> B
```

## 5. Sequence (MVP USB Serial)

```mermaid
sequenceDiagram
  participant T as Train(Tag)
  participant P as Pico 2 W Node
  participant BR as Serial Bridge (Pi)
  participant MQ as Mosquitto
  participant BE as Backend
  participant UI as Web UI

  T->>P: RFID tag detected
  P->>BR: serial event: rfid.detected
  BR->>MQ: publish MQTT event
  MQ->>BE: deliver event
  BE->>BE: apply rule
  BE->>MQ: publish switch.command
  MQ->>BR: deliver command
  BR->>P: serial command: switch.set
  P->>P: servo move
  P->>BR: serial state ack
  BR->>MQ: publish switch.state
  BE->>UI: SSE state update
```

## 6. Deployment diagram

```mermaid
flowchart TB
  subgraph Pi[Raspberry Pi]
    BR[serial-mqtt-bridge service]
    M[mosquitto container]
    B[backend container]
    F[frontend container]
  end

  subgraph Rail[Railway Layout]
    N1[Pico node-1 via USB]
    N2[Pico node-2 future]
  end

  Laptop[Operator browser]

  N1 <-- USB Serial --> BR
  N2 <-- Wi-Fi MQTT future --> M
  B <-- local docker net --> M
  F <-- HTTP --> B
  Laptop --> F
```

## 7. Важные electrical ограничения

- Pico GPIO работают на **3.3V**.
- Нельзя подавать 5V сигналы напрямую на GPIO Pico.
- MFRC522 должен работать от 3.3V.
- Servo питаются от внешнего 5V источника, не от Pico.
- **COMMON GND обязателен** между Pico, MFRC522 и servo power.

## 8. Границы ответственности

### Pico firmware
- Debounce/anti-duplicate RFID чтений.
- Исполнение servo-команды.
- Heartbeat и диагностика (uptime/errors).
- Serial protocol (MVP), future: MQTT over Wi‑Fi.

### Backend
- Каноническая модель layout и устройств.
- Rule evaluation (stateless/stateful).
- Источник истины по текущему состоянию.
- API для UI и внешних интеграций.

### UI
- Визуализация состояния.
- Журнал событий.
- Manual override (подтверждённые команды).
