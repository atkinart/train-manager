# Wiring table (MVP, Pico 2 W)

## 1) Pico + 1x MFRC522 + 2x servo

| From | Pin | To | Pin | Domain | Purpose | Notes |
|---|---|---|---|---|---|---|
| Pico 2 W | 3V3(OUT) | MFRC522 | 3.3V | POWER 3.3V | Reader power | Never 5V |
| Pico 2 W | GND | MFRC522 | GND | COMMON GND | Ground reference | Required |
| Pico 2 W | GP18 | MFRC522 | SCK | SPI BUS | Clock | Short wires |
| Pico 2 W | GP19 | MFRC522 | MOSI | SPI BUS | Data out | |
| Pico 2 W | GP16 | MFRC522 | MISO | SPI BUS | Data in | |
| Pico 2 W | GP17 | MFRC522 | SDA/SS | SPI BUS | CS | One reader CS |
| Pico 2 W | GP20 | MFRC522 | RST | SIGNAL | Reader reset | Optional but recommended |
| Pico 2 W | GP14 | Servo #1 | Signal | PWM | Switch #1 control | Signal only |
| Pico 2 W | GP15 | Servo #2 | Signal | PWM | Switch #2 control | Signal only |
| External 5V PSU | +5V | Servo #1/#2 | V+ | POWER 5V | Servo power | Never from Pico |
| External 5V PSU | GND | Servo #1/#2 | GND | COMMON GND | Servo return | Required |
| External 5V PSU | GND | Pico 2 W | GND | COMMON GND | Shared reference | Critical |
| Raspberry Pi | USB-A | Pico 2 W | USB | USB SERIAL | Data + Pico power | MVP default |

## 2) Pico + 2x MFRC522 (future-near)

| Shared lines | Pins |
|---|---|
| SCK | GP18 |
| MOSI | GP19 |
| MISO | GP16 |
| RST | GP20 (optional shared) |

Separate CS lines:
- Reader #1 CS → GP17
- Reader #2 CS → GP21

## 3) Safety notes

- Нельзя подавать 5V логический сигнал на Pico GPIO.
- Нельзя питать servo от Pico.
- Без COMMON GND servo signal будет нестабильным.
