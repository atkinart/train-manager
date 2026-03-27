# Arduino firmware skeleton

Целевая архитектура: Arduino node публикует события в MQTT и получает команды.

## Два варианта связи

- **Вариант A (целевой):** Arduino напрямую MQTT (Wi-Fi/Ethernet shield).
- **Вариант B (упрощённый старт):** Arduino -> Serial -> bridge (на Raspberry Pi), а bridge уже MQTT.

В этом репо зафиксирована **целевая MQTT-модель**, но для первых шагов допускается Variant B.

## Папки
- `node_mvp/src/node_mvp.ino` — основной скетч-каркас.

## Что уже заложено
- pin config;
- abstraction для reader и servo switch;
- anti-duplicate RFID reads;
- heartbeat заготовка;
- command handler заготовка.

## Прошивка
1. Установить Arduino IDE.
2. Добавить библиотеки: `MFRC522`, `Servo`, MQTT lib (зависит от выбранной сетевой библиотеки).
3. Открыть `node_mvp/src/node_mvp.ino`.
4. Заполнить Wi-Fi/MQTT параметры.
5. Загрузить на плату и смотреть Serial Monitor.
