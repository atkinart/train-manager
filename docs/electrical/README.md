# Electrical docs (Pico 2 W)

Этот раздел описывает электрическую часть для MVP на **Raspberry Pi Pico 2 W / Pico 2 WH**.

## Основные документы

- `wiring-overview.md`
- `wiring-table-mvp.md`
- `power-design.md`
- `mfrc522-connection.md`
- `servo-connection.md`
- `pico-rpi-connection.md`
- `common-mistakes.md`
- `pca9685-future-expansion.md`

## Главные правила

1. GPIO Pico: только 3.3V.
2. Servo: отдельный 5V PSU.
3. Всегда COMMON GND.
4. MVP transport: USB Serial Pico ↔ Raspberry Pi.


## Legacy files

- `arduino-rpi-connection.md` и `diagrams/arduino-rpi-serial.*` оставлены только как historical reference.
- Для текущего проекта используйте `pico-rpi-connection.md` и `diagrams/pico-rpi-serial.svg`.
