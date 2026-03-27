#pragma once

#include <Servo.h>

class ServoSwitch {
public:
  ServoSwitch(int pin, int straight, int diverge)
      : pin(pin), straightAngle(straight), divergeAngle(diverge) {}

  void begin() { servo.attach(pin); }

  void setStraight() {
    currentState = "STRAIGHT";
    servo.write(straightAngle);
  }

  void setDiverge() {
    currentState = "DIVERGE";
    servo.write(divergeAngle);
  }

  const String &state() const { return currentState; }

private:
  int pin;
  int straightAngle;
  int divergeAngle;
  String currentState = "UNKNOWN";
  Servo servo;
};
