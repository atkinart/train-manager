# Arduino firmware skeleton (Legacy / Alternative)

Этот раздел сохранён как исторический и совместимый путь, но **не является primary платформой проекта**.

## Текущий статус

- Основная платформа узла: **Raspberry Pi Pico 2 W / Pico 2 WH**.
- Основной firmware path: `firmware/pico2w/`.
- Этот `arduino/` каталог оставлен для:
  - существующих Arduino макетов;
  - сравнения подходов;
  - миграции старых экспериментов.

## Если вы начинаете с нуля

Используйте Pico-centric путь:
1. `docs/wiring-guide.md`
2. `docs/power-design.md`
3. `firmware/pico2w/README.md`

## Что здесь лежит

- `node_mvp/src/node_mvp.ino` — ранний Arduino sketch-каркас (legacy).
