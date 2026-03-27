# PCA9685 future expansion

Когда servo станет больше (4+), лучше вынести PWM на PCA9685.

## Идея

- Pico 2 W управляет PCA9685 по I2C.
- PCA9685 управляет множеством servo.
- Servo всё равно питаются от внешнего 5V PSU.
- COMMON GND обязателен.

## Базовые связи

| Pico 2 W | PCA9685 | Role |
|---|---|---|
| GP0 | SDA | I2C data |
| GP1 | SCL | I2C clock |
| 3V3(OUT) | VCC | Logic power |
| GND | GND | Common ground |
| External 5V PSU +5V | V+ | Servo power rail |
