# Implementation Plan

## Этап 0: Подготовка (1-2 дня)
- Сформировать BOM и заказать компоненты с акцентом на Pico 2 W/WH.
- Поднять локальную инфраструктуру (mosquitto/backend/frontend).
- Зафиксировать ID-схему node/reader/switch/tag.
- Определить формат Serial протокола Pico ↔ Raspberry Pi bridge.

**Готовность:** инфраструктура поднимается, документы согласованы, понятен MVP transport.

## Этап 1: MVP hardware smoke (3-5 дней)
- Подключить 1 Pico 2 W + 1 MFRC522 + 2 servo.
- Проверить отдельные firmware smoke-тесты reader и servo.
- Проверить heartbeat через USB Serial в Raspberry Pi.

**Готовность:** стабильный heartbeat, читается UID, обе servo двигаются по локальной команде.

## Этап 2: MVP integration (5-7 дней)
- Публикация `rfid.detected` через serial bridge в MQTT.
- Backend получает событие и применяет базовое правило.
- Backend отправляет `switch.command`, bridge передаёт в Pico.
- Pico подтверждает `switch.state`, UI показывает онлайн-статус и event log.

**Готовность:** end-to-end сценарий на 1 reader + 2 switch.

## Этап 3: Целевой MVP (1-2 недели)
- Добавить вторую точку считывания (или второй узел).
- Добавить device registry и layout модель.
- Ручное переключение стрелок из UI.
- Базовые интеграционные тесты backend + bridge.

**Готовность:** 2 стрелки управляются автоматически и вручную.

## Этап 4: Future architecture
- Переход с USB Serial на Wi‑Fi MQTT direct для Pico 2 W.
- 3+ node, 10+ switches.
- PCA9685 при росте числа servo.
- История событий в БД.
- Rule DSL редактор и валидация.

## Риски и меры

| Риск | Влияние | Мера |
|---|---|---|
| Подача 5V сигнала на GPIO Pico | риск повреждения контроллера | строго проверять уровни, только 3.3V logic |
| Просадка питания servo | хаотичная работа/ресеты | отдельный 5V БП, common GND, конденсатор |
| Шум на RFID | ложные срабатывания | anti-duplicate окно + фильтры |
| Потеря связи bridge | пропуск команд | heartbeat + retry + буферизация bridge |
| Сложность правил | трудная поддержка | JSON DSL + тестовые сценарии |

## Критерии готовности MVP
- End-to-end latency < 500 ms в локальной сети (целевая).
- Не менее 30 минут непрерывной работы без reset Pico node.
- 0 ложных переключений на тестовой трассе 20 циклов.
- Стабильный обмен Pico↔Pi по USB Serial без потери heartbeat.
