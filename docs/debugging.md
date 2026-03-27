# Debugging Guide

## Порядок отладки (обязательно)

1. **Reader отдельно**: убедиться, что UID читается стабильно в Serial.
2. **Servo отдельно**: проверить крайние положения и отсутствие reset.
3. **MQTT отдельно**: heartbeat publish/subscribe без бизнес-логики.
4. **Backend**: приём события и расчёт команды.
5. **UI**: отображение live state.

## Быстрые проверки

### MQTT
```bash
mosquitto_sub -h localhost -t 'brio/v1/#' -v
```

### Backend state
```bash
curl http://localhost:8080/api/v1/state
```

## Таблица типовых проблем

| Проблема | Где искать | Действие |
|---|---|---|
| Нет heartbeat | Wi-Fi/MQTT creds | проверить broker host/port/clientId |
| Есть heartbeat, нет RFID events | wiring SPI/питание 3.3V | сверить пины и питание reader |
| Команда есть, стрелка не двигается | servo power | отдельный 5V БП + общая земля |
| UI пустой | SSE/REST CORS | проверить `/api/v1/events/stream` и CORS |
| Дубли RFID событий | нет anti-duplicate окна | включить фильтр повторов в node |

## Практический совет

На каждом шаге фиксируйте эталонный лог успешного запуска — это ускоряет диагностику регрессий.
