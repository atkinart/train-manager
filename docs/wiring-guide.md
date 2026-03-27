# Wiring guide (Pico 2 W MVP)

## 1) Главные правила

1. Pico GPIO работают на **3.3V**.
2. На GPIO Pico **нельзя подавать 5V**.
3. Servo питаются от **отдельного 5V источника**.
4. Всегда делаем **COMMON GND** между Pico, RFID и servo PSU.

## 2) MFRC522 ↔ Pico 2 W (SPI)

Пример pin mapping для MVP:

- Pico `GP18` → MFRC522 `SCK`
- Pico `GP19` → MFRC522 `MOSI`
- Pico `GP16` → MFRC522 `MISO`
- Pico `GP17` → MFRC522 `SDA/SS` (CS)
- Pico `GP20` → MFRC522 `RST`
- Pico `3V3(OUT)` → MFRC522 `3.3V`
- Pico `GND` → MFRC522 `GND`

## 3) Servo ↔ Pico 2 W

- Pico `GP14` → Servo #1 signal
- Pico `GP15` → Servo #2 signal
- Servo `V+` → external PSU +5V
- Servo `GND` → external PSU GND
- External PSU GND → Pico GND (**COMMON GND**)

## 4) Pico ↔ Raspberry Pi (MVP transport)

- Подключение по USB data cable.
- Этот канал даёт serial transport и обычно питание Pico.
- Это проще и стабильнее для первого запуска, чем сразу Wi‑Fi.

## 5) Future transport

- После стабилизации node firmware и командного протокола можно перейти на direct Wi‑Fi MQTT.
