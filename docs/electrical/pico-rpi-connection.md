# Pico 2 W ↔ Raspberry Pi connection (MVP)

## Почему USB Serial — лучший MVP

- минимум настроек для новичка,
- один кабель: связь + питание Pico,
- легче диагностировать чем Wi‑Fi на первом этапе.

## Физическое подключение

1. Подключите Pico 2 W к USB порту Raspberry Pi.
2. На Raspberry Pi проверьте появление serial device.
3. Запустите bridge процесс, который переводит serial сообщения в MQTT.

Схема: `diagrams/pico-rpi-serial.svg`.

## Future option

После стабилизации логики можно перейти на direct Wi‑Fi MQTT с Pico 2 W.
