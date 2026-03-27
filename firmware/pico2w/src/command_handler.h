#pragma once

#include <Arduino.h>

#include "servo_ctrl.h"

class CommandHandler {
 public:
  explicit CommandHandler(ServoController& servo) : servo_(servo) {}
  bool handleLine(const String& line);

 private:
  ServoController& servo_;
};
