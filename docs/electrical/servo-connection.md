# Servo Connection (Pico 2 W)

## Минимальная схема для 2 servo

- PWM signal:
  - GP14 -> Servo #1 signal
  - GP15 -> Servo #2 signal
- Power:
  - external 5V PSU -> Servo VCC
  - external PSU GND -> Servo GND
  - Pico GND -> common GND

## Почему питание отдельно

Даже маленькие servo могут кратковременно потреблять большой ток.
Если питать через Pico, возможны reset и хаотичное поведение.

## Рекомендации

- Поставьте 470-1000µF между 5V и GND рядом с servo.
- Проверяйте механический упор стрелки: слишком тугой ход = лишний ток.
