# Electrical power design

## Два домена питания

1. **3.3V logic domain**: Pico 2 W + MFRC522.
2. **5V power domain**: servo motors.

Обе части связаны только через общий GND и signal lines.

## Обязательные правила

- Pico GPIO — только 3.3V.
- MFRC522 — питание 3.3V.
- Servo — отдельное 5V питание.
- COMMON GND обязателен.

## Запреты

- Не подавать 5V в GPIO Pico.
- Не питать servo от Pico.
- Не подключать 5V UART напрямую в Pico RX.

## Минимальная проверка

1. Pico + MFRC522 стабильно работают.
2. Servo подключены к внешнему 5V PSU.
3. COMMON GND прозвонен.
