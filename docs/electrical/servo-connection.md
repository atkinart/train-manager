# Servo connection

## У servo обычно 3 провода

Обычно у micro-servo:
- красный: `POWER 5V`
- коричневый/чёрный: `GND`
- жёлтый/оранжевый/белый: `servo signal`

## Важное различие: питание vs сигнал

- `POWER 5V` и `GND` — это питание двигателя servo.
- `servo signal` — это только управляющий импульс.

**Сигнальный провод не может питать servo.**

## Как подключить 2 servo в MVP

1. Подключите `servo signal`:
   - Servo #1 signal → Arduino D5
   - Servo #2 signal → Arduino D6
2. Подключите питание от внешнего блока:
   - +5V PSU → V+ обоих servo
   - GND PSU → GND обоих servo
3. Соедините землю PSU с Arduino GND (`COMMON GND`).

## Почему нельзя питать servo от Arduino 5V

Потому что 2 servo могут потреблять ток больше, чем безопасно для Arduino/USB.

Последствия:
- просадка напряжения,
- reset Arduino,
- нестабильный RFID,
- «дёргание» стрелок.

## Мини-проверка после подключения

- На servo есть отдельный `POWER 5V`.
- `servo signal` идут в нужные пины.
- `COMMON GND` между PSU и Arduino есть.
- Сначала тестируется движение без механической нагрузки.

## Как выглядит переход на PCA9685

Когда servo становится больше (например 4, 8, 12 и т.д.), лучше вынести генерацию PWM на PCA9685:

- Arduino управляет PCA9685 по I2C.
- PCA9685 отдаёт много `servo signal` каналов.
- Питание servo всё равно остаётся внешним `POWER 5V`.

Подробнее: `pca9685-future-expansion.md` и `diagrams/future-pca9685.svg`.
