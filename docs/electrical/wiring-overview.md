# Wiring overview (обзор подключения)

## Что есть в MVP-узле

В первом рабочем варианте (MVP) у вас есть:

- Arduino UNO R4 WiFi (или совместимая Arduino)
- MFRC522 (RFID)
- 2 servo для стрелок
- Raspberry Pi
- Внешний блок питания `POWER 5V` для servo
- Общая земля `COMMON GND`

## Базовая идея очень простыми словами

- **Питание** и **сигналы** — это не одно и то же.
- У servo есть отдельное питание `POWER 5V` и отдельный `servo signal`.
- MFRC522 — это 3.3V-устройство: ему нужен `POWER 3.3V`.
- Чтобы сигналы работали корректно, у всех устройств должна быть общая земля: `COMMON GND`.

## Какие схемы смотреть сначала

1. `diagrams/mvp-node.svg` — основной MVP wiring.
2. `diagrams/power-distribution.svg` — только питание и земля.
3. `diagrams/arduino-rpi-serial.svg` — только Arduino ↔ Raspberry Pi.
4. `mfrc522-connection.md` — отдельные детали SPI и 2 reader.

## Принцип безопасной сборки

**Сначала один reader, потом servo, потом Raspberry Pi.**

1. Подключите только Arduino + 1 MFRC522.
2. Убедитесь, что RFID читается стабильно.
3. Подключите 2 servo сигнальными линиями (`servo signal`).
4. Подключите внешний `POWER 5V` для servo.
5. Проверьте `COMMON GND` между Arduino, RFID, servo PSU.
6. Подключите Raspberry Pi к Arduino через `USB SERIAL`.

## Короткий словарь терминов

- **GND** — общий «ноль», опорный уровень для сигналов.
- **COMMON GND** — когда GND разных источников/устройств связаны вместе.
- **POWER 5V** — силовое питание 5 В (в MVP — в первую очередь для servo).
- **POWER 3.3V** — питание 3.3 В (для MFRC522).
- **SPI BUS** — шина связи Arduino ↔ MFRC522 (SCK/MOSI/MISO + CS).
- **USB SERIAL** — самый простой стартовый канал Arduino ↔ Raspberry Pi.
