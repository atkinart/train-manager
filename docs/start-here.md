# Start here (beginner-first)

Если вы впервые собираете этот проект, идите строго по шагам ниже.

## 1) Что купить

Смотрите `docs/bom.md`.

Минимальный стартовый комплект:
- Pico 2 W или Pico 2 WH,
- 1x MFRC522,
- 2x servo,
- внешний 5V PSU для servo (2A+),
- Raspberry Pi,
- USB data cable,
- breadboard/jumper wires.

## 2) Что важно про безопасность

Перед первым включением обязательно:
- Pico GPIO — только 3.3V,
- нельзя подавать 5V в GPIO Pico,
- servo питаются от внешнего 5V,
- COMMON GND обязателен.

Ссылки:
- `docs/wiring-guide.md`
- `docs/power-design.md`
- `docs/electrical/common-mistakes.md`

## 3) Минимально безопасный порядок подключения

1. Подключите только Pico + MFRC522 (без servo питания).
2. Проверьте чтение RFID.
3. Подключите только signal-провода servo к Pico.
4. Подключите внешний 5V PSU к servo.
5. Соедините земли (COMMON GND).
6. Подключите Pico к Raspberry Pi по USB Serial.

## 4) Что запускать по софту

1. Прошивка: `firmware/pico2w/`.
2. MQTT schema: `docs/mqtt-topics.md`.
3. Backend: `backend/README.md`.
4. Frontend: `frontend/README.md`.

## 5) Как понять, что MVP ожил

- Периодический heartbeat,
- RFID событие читается один раз (без спама дублей),
- команда `switch.set` меняет состояние стрелки,
- backend получает событие и показывает состояние в API/UI.
