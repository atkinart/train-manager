# BOM (Bill of Materials)

## Core (MVP)

| Item | Qty | Required | Notes |
|---|---:|---|---|
| Raspberry Pi Pico 2 W или Pico 2 WH | 1 | Yes | Основной контроллер узла |
| MFRC522 RFID module | 1 | Yes | SPI reader |
| Servo (SG90/MG90S class) | 2 | Yes | Стрелки |
| External 5V PSU для servo (2A+ recommended) | 1 | Yes | Отдельное силовое питание servo |
| Raspberry Pi (любой современный) | 1 | Yes | Hub + Mosquitto + bridge |
| USB cable (data) | 1 | Yes | Pico ↔ Raspberry Pi |
| Breadboard + jumper wires | 1 set | Yes | Прототипирование |

## Pico 2 W vs Pico 2 WH

- **Pico 2 WH**: рекомендуется для быстрого старта, потому что header pins уже припаяны.
- **Pico 2 W**: удобнее для компактной и кастомной интеграции.
- Функционально контроллеры одинаковы.

## Optional / Future

| Item | Why |
|---|---|
| PCA9685 | Масштабирование по servo (4+) |
| Additional MFRC522 | Несколько RFID точек |
| Dedicated 5V DC-DC modules | Чистая силовая архитектура |

## Platform note

Arduino не является базовой платформой проекта. Если используется, то только как alternative/legacy путь.
