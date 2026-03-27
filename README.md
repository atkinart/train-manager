# Train Manager (BRIO automation)

Проект автоматизации BRIO-совместимого макета железной дороги с RFID, **Raspberry Pi Pico 2 W / Pico 2 WH**, MQTT, Spring Boot backend и web UI.

> Статус платформы: **Pico 2 W — основной контроллер узла**. Arduino-путь сохранён только как legacy/alternative.

## Целевая архитектура

1. **Node level (MVP)**: Pico 2 W + MFRC522 + 2 servo.
2. **Transport (MVP)**: USB Serial между Pico 2 W и Raspberry Pi.
3. **Hub level**: Raspberry Pi + Mosquitto + bridge process.
4. **Application level**: Spring Boot backend + JS web UI.

Future mode:
- прямой Wi‑Fi MQTT с Pico 2 W,
- несколько Pico-узлов,
- расширение на PCA9685 для большого числа servo,
- блок-участки, сигналы и гейты.

## Почему Pico 2 W как primary controller

- 3.3V-логика хорошо совпадает с MFRC522.
- Низкая цена и доступность.
- Есть путь роста в Wi‑Fi/MQTT без смены контроллера.
- Pico 2 WH удобен для старта (pins уже припаяны), Pico 2 W удобен для компактной интеграции.

## Быстрый старт (MVP)

1. Соберите wiring по `docs/wiring-guide.md`.
2. Проверьте питание по `docs/power-design.md`.
3. Прошейте `firmware/pico2w/` (Arduino IDE + Arduino-Pico core).
4. Подключите Pico к Raspberry Pi по USB.
5. Запустите serial↔MQTT bridge на Raspberry Pi.
6. Проверьте heartbeat и RFID-события в MQTT (`docs/mqtt-topics.md`).

## Структура репозитория

- `docs/` — архитектура, wiring, питание, roadmap, debug.
- `docs/electrical/` — пошаговая электрическая документация и схемы.
- `firmware/pico2w/` — основной firmware skeleton для Pico 2 W.
- `arduino/` — legacy/alternative path (не primary).
- `frontend/` — web UI.
- `examples/` — примеры payload/конфигураций.

## Migration from Arduino plan to Pico 2 W

Что изменено:
- Pico 2 W/Pico 2 WH стал основной платформой node-контроллера.
- MVP transport закреплён как USB Serial Pico ↔ Raspberry Pi.
- Вся документация по питанию переведена на 3.3V logic + external 5V servo power + common ground.
- Firmware skeleton перенесён в `firmware/pico2w/`.

Что осталось legacy:
- `arduino/` сохранён как исторический/альтернативный путь и не считается основной веткой развития.

## Безопасность электрической части (обязательно)

- Нельзя подавать 5V сигнал напрямую на GPIO Pico 2 W.
- Нельзя питать servo от Pico.
- Всегда делайте общую землю (Pico GND + servo PSU GND + RFID GND).
- Отлаживайте поэтапно: сначала RFID, потом servo, потом bridge/MQTT.
