#pragma once

#include <Arduino.h>

class SerialTransport {
 public:
  void begin(unsigned long baud);
  void sendLine(const String& line);
  bool readLine(String& out_line);
};
