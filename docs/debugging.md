# Debugging Guide (Pico 2 W MVP)

## Порядок отладки (обязательно)

1. **RFID отдельно**: убедиться, что UID читается стабильно в USB Serial лог.
2. **Servo отдельно**: проверить крайние положения и отсутствие reset Pico.
3. **Serial transport отдельно**: heartbeat из Pico до Raspberry Pi bridge.
4. **MQTT отдельно**: bridge публикует heartbeat/event в broker.
5. **Backend**: приём события и расчёт команды.
6. **UI**: отображение live state.

## Быстрые проверки

### MQTT
```bash
mosquitto_sub -h localhost -t 'brio/v1/#' -v
```

### Backend state
```bash
curl http://localhost:8080/api/v1/state
```

### Проверка USB устройства (на Raspberry Pi)
```bash
ls /dev/ttyACM* /dev/ttyUSB*
```

## Таблица типовых проблем

| Проблема | Где искать | Действие |
|---|---|---|
| Нет heartbeat | USB Serial bridge / порт | проверить `/dev/ttyACM*`, baudrate, права |
| Есть heartbeat, нет RFID events | wiring SPI/питание 3.3V | сверить SPI pin mapping и питание MFRC522 |
| Команда есть, стрелка не двигается | servo power | отдельный 5V БП + common GND |
| Pico нестабилен | 5V шум/неправильная земля | сократить провода, улучшить GND, добавить конденсатор |
| UI пустой | SSE/REST CORS | проверить `/api/v1/events/stream` и CORS |
| Дубли RFID событий | нет anti-duplicate окна | включить suppress duplicate tags в firmware |

## Практические советы новичку

- Логи Serial держите максимально простыми (`[HB]`, `[RFID]`, `[CMD]`, `[ERR]`).
- Любая нестабильность сначала трактуется как проблема питания/земли.
- Меняйте только один параметр за шаг, затем повторяйте тест.
- Сохраните эталонный лог успешного запуска — это ускоряет поиск регрессий.
