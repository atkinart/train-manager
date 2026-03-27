# Pico 2 W firmware skeleton (primary path)

## Target

- Board: Raspberry Pi Pico 2 W / Pico 2 WH
- MVP transport: USB Serial to Raspberry Pi
- Peripherals: MFRC522 (SPI), 2x servo (PWM signal)

## Safety invariants (must keep)

- Pico GPIO = 3.3V logic only.
- Never apply 5V logic to Pico GPIO.
- Servo are powered by external 5V PSU.
- COMMON GND is mandatory (Pico + MFRC522 + servo PSU).

## Development modes

### MVP recommended

- Arduino IDE + Arduino-Pico core
- Быстрый старт для beginner-friendly workflow

### Future option

- Official Pico C/C++ SDK

## Structure

- `src/main.ino` — main loop и orchestration
- `src/pins.h` — pin mapping
- `src/rfid.h` — RFID abstraction
- `src/servo_ctrl.h` — servo abstraction
- `src/serial_transport.h` — serial protocol wrapper
- `src/command_handler.h` — command skeleton

## MVP behavior

- heartbeat в serial channel,
- RFID scan,
- duplicate tag suppression window,
- command handling for 2 switches.

## Naming alignment

Для совместимости с backend/docs/examples используйте:
- `nodeId`: `node-1`
- `readerId`: `reader-a`
- `switchId`: `sw-1`, `sw-2`

MQTT naming и payload schema описаны в `docs/mqtt-topics.md`.
