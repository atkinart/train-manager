# Wiring Overview (MVP)

## MVP состав

- 1x Raspberry Pi Pico 2 W/WH
- 1x MFRC522 (SPI)
- 2x Servo (PWM)
- 1x Raspberry Pi (USB Serial bridge + MQTT/backend)

## Mermaid overview

```mermaid
flowchart LR
  PICO[Pico 2 W / 2 WH] <-->|USB Serial| PI[Raspberry Pi]
  PICO <-->|SPI 3.3V| RFID[MFRC522]
  PICO -->|PWM| S1[Servo #1]
  PICO -->|PWM| S2[Servo #2]
  PSU[External 5V PSU] --> S1
  PSU --> S2

  G[(COMMON GND)] --- PICO
  G --- RFID
  G --- S1
  G --- S2
```

## Что проверить до включения

- Нет 5V на GPIO Pico.
- MFRC522 питается от 3.3V.
- Servo VCC подключены только к внешнему 5V PSU.
- Все GND объединены в common ground.
