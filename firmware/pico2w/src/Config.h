#pragma once

namespace Config {
  // SPI for MFRC522
  constexpr int RFID_SCK = 2;
  constexpr int RFID_MOSI = 3;
  constexpr int RFID_MISO = 4;
  constexpr int RFID_SS = 5;
  constexpr int RFID_RST = 6;

  // PWM for servo
  constexpr int SERVO_1_PWM = 14;
  constexpr int SERVO_2_PWM = 15;

  constexpr unsigned long HEARTBEAT_MS = 5000;
  constexpr unsigned long DUP_WINDOW_MS = 1500;
}
