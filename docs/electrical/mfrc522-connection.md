# MFRC522 connection (Pico 2 W, SPI)

## Что важно

- MFRC522 работает с 3.3V логикой.
- Это естественная связка с Pico 2 W (тоже 3.3V).
- SPI провода должны быть короткими.

## Pin mapping (MVP)

| Pico 2 W | MFRC522 | Role |
|---|---|---|
| GP18 | SCK | SPI clock |
| GP19 | MOSI | SPI data to reader |
| GP16 | MISO | SPI data from reader |
| GP17 | SDA/SS | CS |
| GP20 | RST | Reset |
| 3V3(OUT) | 3.3V | Power |
| GND | GND | Common ground |

## При двух reader

- SPI (SCK/MOSI/MISO) общие,
- у каждого reader свой CS,
- общий reset допустим для MVP.
