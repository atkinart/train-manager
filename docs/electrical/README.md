# Электрика и подключение BRIO-узла

Этот раздел — пошаговый гид для новичка. Здесь объяснено, **что купить**, **куда какой провод подключать**, где **5V**, где **3.3V**, где **GND**, и что **нельзя** делать.

## Какие файлы есть

- `wiring-overview.md` — общий обзор и порядок сборки.
- `wiring-table-mvp.md` — главные таблицы соединений (MVP, 2x MFRC522, питание, PCA9685).
- `mvp-step-by-step.md` — безопасный пошаговый сценарий первого запуска.
- `power-design.md` — архитектура питания, варианты и рекомендованный MVP-вариант.
- `common-mistakes.md` — частые ошибки и опасные действия.
- `arduino-rpi-connection.md` — как подключать Arduino к Raspberry Pi (старт через USB Serial).
- `mfrc522-connection.md` — подробное подключение RFID MFRC522 (1 и 2 reader).
- `servo-connection.md` — подробное подключение 2 servo и основы по `servo signal`.
- `pca9685-future-expansion.md` — как расширяться на много servo через PCA9685.
- `diagrams/*.svg` — визуальные схемы.
- `diagrams/*.drawio` — исходники для draw.io (можно открыть и править).

## С чего начать новичку

1. Прочитать `wiring-overview.md`.
2. Прочитать `power-design.md` (особенно про **COMMON GND**).
3. Пройти `mvp-step-by-step.md`.
4. Подключить только Arduino + 1 MFRC522 по `mfrc522-connection.md`.
5. После проверки RFID добавить servo по `servo-connection.md`.
6. Только потом подключить Raspberry Pi по `arduino-rpi-connection.md`.
7. Перед первым запуском проверить себя по `common-mistakes.md`.

## Минимально безопасный порядок подключения

1. Питание выключено.
2. Собрать `POWER 3.3V` + `SPI BUS` для одного MFRC522.
3. Проверить, что MFRC522 читается.
4. Подключить `servo signal` к Arduino (без силового 5V servo).
5. Подключить внешний `POWER 5V` для servo.
6. Объединить земли в одну точку: `COMMON GND`.
7. Проверить полярность питания ещё раз.
8. Подключить Arduino к Raspberry Pi по `USB SERIAL`.
9. Только после этого подавать питание на всю систему.

## Что относится к MVP, а что к будущему расширению

### MVP (сейчас)
- 1 Arduino node
- 1 Raspberry Pi
- 1 MFRC522
- 2 servo
- Отдельный `POWER 5V` для servo
- `USB SERIAL` между Arduino и Raspberry Pi

### Future expansion (позже)
- 2+ MFRC522 на одной Arduino (общий `SPI BUS`, отдельные `CS`)
- PCA9685 для большего количества servo
- Несколько Arduino node
- Возможный переход с USB на другие интерфейсы
