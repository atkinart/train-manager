# Wiring overview (MVP)

## Hardware MVP

- Pico 2 W / Pico 2 WH
- 1x MFRC522
- 2x servo
- External 5V PSU for servo
- Raspberry Pi (USB Serial + MQTT stack)

## Диаграммы

- `diagrams/mvp-node.svg`
- `diagrams/mvp-node-2-mfrc522.svg`
- `diagrams/power-distribution.svg`
- `diagrams/pico-rpi-serial.svg`
- `diagrams/future-pca9685.svg`

## Пошагово

1. Подключите Pico + MFRC522 (SPI, 3.3V).
2. Проверьте чтение RFID.
3. Подключите servo signal к PWM GPIO Pico.
4. Подключите отдельный 5V PSU к servo.
5. Соедините земли в COMMON GND.
6. Подключите Pico к Raspberry Pi по USB.
