# Wiring tables (таблицы соединений)

Ниже — основные таблицы в формате `From -> To`.

## 1) MVP wiring (1x MFRC522 + 2x servo + Raspberry Pi)

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Arduino UNO R4 | 3.3V | MFRC522 | 3.3V | POWER 3.3V | Питание reader | **Никогда не 5V** |
| Arduino UNO R4 | GND | MFRC522 | GND | COMMON GND | Общая земля | Обязательно |
| Arduino UNO R4 | D13 (SCK) | MFRC522 | SCK | SPI BUS | Clock | Общая SPI-линия |
| Arduino UNO R4 | D11 (MOSI) | MFRC522 | MOSI | SPI BUS | Data to reader | Общая SPI-линия |
| Arduino UNO R4 | D12 (MISO) | MFRC522 | MISO | SPI BUS | Data from reader | Общая SPI-линия |
| Arduino UNO R4 | D10 (SS/CS) | MFRC522 | SDA/SS | SPI BUS | Выбор reader | Для 1 reader достаточно одного CS |
| Arduino UNO R4 | D9 | MFRC522 | RST | SIGNAL | Сброс reader | Можно общий RST для нескольких reader |
| Arduino UNO R4 | D5 | Servo #1 | Signal | servo signal | Управление стрелкой 1 | Только сигнал, не питание |
| Arduino UNO R4 | D6 | Servo #2 | Signal | servo signal | Управление стрелкой 2 | Только сигнал, не питание |
| External 5V PSU | +5V | Servo #1 | V+ (red) | POWER 5V | Питание servo #1 | Отдельный блок питания |
| External 5V PSU | +5V | Servo #2 | V+ (red) | POWER 5V | Питание servo #2 | Отдельный блок питания |
| External 5V PSU | GND | Servo #1 | GND (brown/black) | COMMON GND | Земля servo #1 | Обязательно |
| External 5V PSU | GND | Servo #2 | GND (brown/black) | COMMON GND | Земля servo #2 | Обязательно |
| External 5V PSU | GND | Arduino UNO R4 | GND | COMMON GND | Общая земля между питанием и контроллером | Критично для servo signal |
| Raspberry Pi | USB-A port | Arduino UNO R4 | USB-C/USB-B (по кабелю) | USB SERIAL | Данные Arduino↔Pi | Самый простой старт |

## 2) Wiring для 2x MFRC522 на одной Arduino

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Arduino UNO R4 | 3.3V | MFRC522 #1 | 3.3V | POWER 3.3V | Питание reader #1 | Проверить токовый запас |
| Arduino UNO R4 | 3.3V | MFRC522 #2 | 3.3V | POWER 3.3V | Питание reader #2 | Reader держать близко к Arduino |
| Arduino UNO R4 | GND | MFRC522 #1/#2 | GND | COMMON GND | Общая земля | Обязательно |
| Arduino UNO R4 | D13 | MFRC522 #1/#2 | SCK | SPI BUS | Общий clock | Линия общая для обоих |
| Arduino UNO R4 | D11 | MFRC522 #1/#2 | MOSI | SPI BUS | Общий MOSI | Линия общая для обоих |
| Arduino UNO R4 | D12 | MFRC522 #1/#2 | MISO | SPI BUS | Общий MISO | Линия общая для обоих |
| Arduino UNO R4 | D10 | MFRC522 #1 | SDA/SS | SPI BUS | CS reader #1 | Отдельный CS |
| Arduino UNO R4 | D8 | MFRC522 #2 | SDA/SS | SPI BUS | CS reader #2 | Отдельный CS |
| Arduino UNO R4 | D9 | MFRC522 #1/#2 | RST | SIGNAL | Общий reset | Удобно для MVP |

## 3) Power distribution (только питание)

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Raspberry Pi PSU | +5V | Raspberry Pi | Power input | POWER 5V | Питание Raspberry Pi | Отдельный блок питания Pi |
| Arduino USB | +5V | Arduino UNO R4 | USB power input | POWER 5V | Питание Arduino | Через USB от Pi или отдельного адаптера |
| Arduino UNO R4 | 3.3V | MFRC522 | 3.3V | POWER 3.3V | Питание RFID | Не превышать 3.3V |
| Servo PSU | +5V | Servo #1/#2 | V+ | POWER 5V | Силовое питание servo | Не брать этот ток из Arduino 5V pin |
| Servo PSU | GND | Arduino + servo + RFID | GND | COMMON GND | Общая опора сигналов | Без этого servo часто «дёргаются» |

## 4) Future wiring с PCA9685

| From device | From pin | To device | To pin | Voltage/domain | Purpose | Notes |
|-------------|----------|-----------|--------|----------------|---------|-------|
| Arduino UNO R4 | SDA | PCA9685 | SDA | I2C / SIGNAL | Данные I2C | Логическая шина |
| Arduino UNO R4 | SCL | PCA9685 | SCL | I2C / SIGNAL | Такт I2C | Логическая шина |
| Arduino UNO R4 | 5V | PCA9685 | VCC | Logic power | Питание логики PCA9685 | Проверить модуль/уровни |
| External 5V PSU | +5V | PCA9685 | V+ (servo power) | POWER 5V | Питание servo через плату | Отдельно от логики |
| External 5V PSU | GND | PCA9685 + Arduino | GND | COMMON GND | Общая земля | Обязательно |
| PCA9685 | CH0 signal | Servo #1 | Signal | servo signal | PWM канал 0 | Масштабирование на много servo |
| PCA9685 | CH1 signal | Servo #2 | Signal | servo signal | PWM канал 1 | И так далее |
