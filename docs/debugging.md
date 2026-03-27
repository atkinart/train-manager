# Debugging guide

## 0) Порядок отладки (обязательный)

1. Питание и земля.
2. RFID.
3. Servo.
4. USB serial.
5. MQTT bridge.

## 1) Частые проблемы

### Проблема: Pico зависает/перезапускается при движении servo

Обычно причина: servo питаются не от отдельного 5V PSU или нет COMMON GND.

### Проблема: MFRC522 не видит метки

Проверьте:
- питание reader на 3.3V,
- SPI pin mapping,
- длину проводов (держать короткими),
- общий GND.

### Проблема: нет данных на Raspberry Pi

Проверьте:
- data USB cable,
- serial device на Pi,
- скорость порта,
- формат строк протокола.

## 2) Быстрые sanity checks

- Heartbeat идёт каждые N секунд.
- Одна и та же метка не спамит событиями (duplicate suppression).
- Команда на стрелку меняет state и приходит ack.

## 3) Логи

- firmware: serial debug output,
- bridge: parse/forward logs,
- backend: MQTT consume/produce logs.
