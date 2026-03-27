# Roadmap

## Version 1 (MVP)
- 1 Pico node (Pico 2 W/WH), 1 reader, 2 switches.
- USB Serial Pico↔Raspberry Pi bridge.
- RFID event -> rule -> switch command -> state ack.
- UI: status + event log + manual switch.

## Version 2
- Несколько Pico node.
- Опциональный переход части узлов на Wi‑Fi MQTT direct.
- Persistent event storage.
- Расширенный rule DSL + тестовые профили маршрутов.
- Улучшенный layout view.

## Version 3
- Block sections occupancy.
- Светофоры и шлагбаумы.
- Улучшенная надёжность (retries, idempotency).
- Role-based access и audit log.

## Дальнейшее расширение
- PCA9685 для большого парка стрелок.
- Полуавтономное расписание маршрутов.
- Digital twin с визуальным проигрыванием событий.
