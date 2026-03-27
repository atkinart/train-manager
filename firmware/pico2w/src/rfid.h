#pragma once

#include <Arduino.h>

struct RfidEvent {
  String uid;
  uint32_t ts_ms;
};

class RfidReader {
 public:
  void begin();
  bool poll(RfidEvent& out_event);
};
