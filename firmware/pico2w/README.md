# Pico 2 W Firmware (Primary path)

Этот раздел — основной firmware path проекта.

## MVP target

- Board: Raspberry Pi Pico 2 W / Pico 2 WH
- Reader: MFRC522 (SPI)
- Actuators: 2 servo (PWM)
- Transport: USB Serial to Raspberry Pi bridge

## Development modes

### Mode A (MVP recommended): Arduino IDE + Arduino-Pico core

Плюсы:
- самый быстрый старт для новичка;
- понятный serial log;
- низкий порог входа.

Шаги:
1. Установить Arduino IDE.
2. Добавить пакет плат **Arduino Mbed OS RP2040 Boards** или совместимый Arduino-Pico core.
3. Выбрать плату Pico 2 W/WH.
4. Установить библиотеки `MFRC522`, `Servo` (или RP2040-совместимую servo библиотеку).
5. Открыть `src/node_mvp_pico2w.ino` и загрузить прошивку.

### Mode B (Future): official Pico C/C++ SDK

Плюсы:
- больше контроля и производительности;
- лучше для production-grade firmware.

Минус:
- выше порог входа.

## Текущая структура

- `src/Config.h` — pin mapping и timing константы.
- `src/ReaderAdapter.h` — RFID abstraction.
- `src/ServoSwitch.h` — servo abstraction.
- `src/SerialTransport.h` — Serial transport abstraction.
- `src/CommandHandler.h` — skeleton command parser.
- `src/node_mvp_pico2w.ino` — основной sketch loop.

## Serial protocol (MVP draft)

- Outbound from Pico (одна JSON строка):
  - `{"type":"heartbeat",...}`
  - `{"type":"rfid.detected",...}`
  - `{"type":"switch.state",...}`
- Inbound to Pico:
  - `{"type":"switch.set","switchId":"sw-1","targetState":"STRAIGHT"}`

## Важно про питание

- Pico GPIO = 3.3V only.
- Servo питаются от внешнего 5V.
- Common GND обязателен.
