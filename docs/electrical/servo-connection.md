# Servo connection (Pico 2 W)

## MVP mapping

- GP14 → Servo #1 signal
- GP15 → Servo #2 signal

## Питание servo

- Только от внешнего 5V PSU.
- Pico подаёт только сигнал управления.
- GND PSU обязательно соединить с GND Pico.

## Почему так

Servo дают пиковые токи. Если питать их от Pico, будут просадки и сбои.
