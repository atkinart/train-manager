#pragma once

#include <Arduino.h>

enum class SwitchPosition {
  STRAIGHT,
  DIVERGE,
};

class ServoController {
 public:
  void begin();
  void setSwitch(uint8_t switch_id, SwitchPosition position);
};
