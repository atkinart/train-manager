# Future expansion: PCA9685 for many servo

## Когда нужен PCA9685

Если у вас уже не 2 servo, а больше (4+), Arduino начинает тратить много ресурсов на PWM и wiring становится сложным.

Тогда лучше использовать PCA9685:
- много каналов `servo signal` с одной платы,
- проще масштабирование,
- чище разводка проводов.

## Базовая идея подключения

- Arduino ↔ PCA9685 по I2C (`SDA`, `SCL`)
- Логика PCA9685 питается от Arduino (обычно 5V/3.3V в зависимости от модуля)
- Servo питаются от отдельного `POWER 5V`
- Везде общий `COMMON GND`

## Мини-таблица

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Arduino UNO R4 | SDA | PCA9685 | SDA | I2C | Data | Логическая шина |
| Arduino UNO R4 | SCL | PCA9685 | SCL | I2C | Clock | Логическая шина |
| Arduino UNO R4 | 5V | PCA9685 | VCC | Logic power | Питание логики | Проверить модуль |
| External 5V PSU | +5V | PCA9685 | V+ | POWER 5V | Силовое питание servo | Не из Arduino |
| External 5V PSU | GND | PCA9685 + Arduino | GND | COMMON GND | Общая земля | Обязательно |
| PCA9685 | CHx | Servo x | signal | servo signal | Управление servo | До 16 каналов на плату |

## Что важно при росте системы

- Делать «звезду» по земле (уменьшает шум).
- Не тянуть слишком длинные силовые провода тонким сечением.
- Проверить суммарный ток всех servo и запас блока питания.
- Разделять силовые и сигнальные жгуты.

Схема: `diagrams/future-pca9685.svg`.
