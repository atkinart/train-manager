# Pico ↔ Raspberry Pi Connection (MVP)

## Почему USB Serial — лучший MVP

- Самое простое физическое подключение.
- Не нужно сразу настраивать Wi‑Fi stack в firmware.
- Легче отлаживать через обычный serial log.

## Физическое подключение

1. Подключить Pico 2 W к Raspberry Pi USB data-кабелем.
2. На Raspberry Pi проверить устройство:

```bash
ls /dev/ttyACM* /dev/ttyUSB*
```

3. Запустить serial bridge, который:
   - читает строки/JSON из Pico;
   - публикует их в MQTT;
   - читает MQTT commands и отправляет обратно в Pico.

## Future path

Позже можно убрать bridge и подключить Pico к MQTT напрямую по Wi‑Fi.

Важно: topic names и payload format лучше оставить прежними, чтобы backend/UI не менять.
