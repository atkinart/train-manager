# Wiring Table MVP (Pico 2 W)

## Pico ↔ MFRC522

| Pico pin | MFRC522 pin | Назначение |
|---|---|---|
| GP5 | SDA/SS | SPI chip select |
| GP2 | SCK | SPI clock |
| GP3 | MOSI | SPI TX |
| GP4 | MISO | SPI RX |
| GP6 | RST | reset |
| 3V3(OUT) | 3.3V | power |
| GND | GND | common ground |

## Pico ↔ Servo

| Pico pin | Servo line | Комментарий |
|---|---|---|
| GP14 | Servo #1 signal | PWM |
| GP15 | Servo #2 signal | PWM |
| GND | Servo GND (через common GND) | обязательно |

## Power

| Источник | Получатель | Комментарий |
|---|---|---|
| USB Raspberry Pi | Pico | питание и data для USB Serial |
| External 5V PSU | Servo VCC | не через Pico |
| External 5V PSU GND | Pico GND | common ground |
