# MFRC522 connection (RFID)

## Что такое SPI простыми словами

`SPI BUS` — это набор проводов, по которым Arduino общается с RFID reader.

Базовые линии:
- `SCK` — тактовый сигнал
- `MOSI` — данные от Arduino к reader
- `MISO` — данные от reader к Arduino
- `CS/SS` — выбор конкретного reader
- `RST` — сброс
- `GND` — общая земля

## Какие линии общие, а какие отдельные

### Общие для всех reader на одной Arduino
- `SCK`, `MOSI`, `MISO`, `GND`, часто `RST`

### Отдельные для каждого reader
- `CS/SS` (у каждого reader свой)

## Почему reader должен быть близко к Arduino

SPI не любит длинные провода, особенно в «шумной» среде с servo.

Поэтому:
- ставьте MFRC522 физически ближе к Arduino,
- делайте линии `SPI BUS` максимально короткими,
- избегайте длинных «лапшевидных» джамперов.

## Таблица подключения (1 reader)

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Arduino UNO R4 | 3.3V | MFRC522 | 3.3V | POWER 3.3V | Питание reader | Не 5V |
| Arduino UNO R4 | GND | MFRC522 | GND | COMMON GND | Общая земля | Обязательно |
| Arduino UNO R4 | D13 | MFRC522 | SCK | SPI BUS | Clock | |
| Arduino UNO R4 | D11 | MFRC522 | MOSI | SPI BUS | Data out | |
| Arduino UNO R4 | D12 | MFRC522 | MISO | SPI BUS | Data in | |
| Arduino UNO R4 | D10 | MFRC522 | SDA/SS | SPI BUS | CS/SS | Reader select |
| Arduino UNO R4 | D9 | MFRC522 | RST | SIGNAL | Reset | |

## Схема подключения 1 reader

Смотрите `diagrams/mvp-node.svg` (блок RFID) и `diagrams/power-distribution.svg`.

Упрощённый ASCII-вид:

```text
Arduino 3.3V  ---- POWER 3.3V ----> MFRC522 3.3V
Arduino GND   ---- COMMON GND ----> MFRC522 GND
Arduino D13   ---- SPI BUS (SCK) -> MFRC522 SCK
Arduino D11   ---- SPI BUS (MOSI)-> MFRC522 MOSI
Arduino D12   ---- SPI BUS (MISO)-> MFRC522 MISO
Arduino D10   ---- SPI BUS (CS) --> MFRC522 SDA/SS
Arduino D9    ---- SIGNAL (RST) -> MFRC522 RST
```

## Схема подключения 2 readers

Для двух reader используйте общую SPI-шину и два CS:

- MFRC522 #1: CS = D10
- MFRC522 #2: CS = D8
- RST можно общий (например D9)

```text
               +--> MFRC522 #1 (CS=D10)
Arduino SPI ---|
               +--> MFRC522 #2 (CS=D8)

Shared lines: SCK/MOSI/MISO/GND/(RST)
Separate line per reader: CS/SS
```

Рекомендуется сначала запустить #1, потом добавлять #2.

Отдельная визуальная схема: `diagrams/mvp-node-2-mfrc522.svg`.
