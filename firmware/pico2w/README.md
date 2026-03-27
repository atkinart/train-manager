# Pico 2 W firmware skeleton (primary path)

## Target

- Board: Raspberry Pi Pico 2 W / Pico 2 WH
- MVP transport: USB Serial to Raspberry Pi
- Peripherals: MFRC522 (SPI), 2x servo (PWM)

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
