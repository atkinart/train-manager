#include <Arduino.h>

#include "command_handler.h"
#include "pins.h"
#include "rfid.h"
#include "serial_transport.h"
#include "servo_ctrl.h"

namespace {
constexpr unsigned long kBaud = 115200;
constexpr uint32_t kHeartbeatEveryMs = 2000;
constexpr uint32_t kDuplicateWindowMs = 1500;

SerialTransport transport;
RfidReader rfid;
ServoController servo;
CommandHandler cmd(servo);

uint32_t last_heartbeat_ms = 0;
String last_uid;
uint32_t last_uid_ms = 0;
}  // namespace

// ===== minimal method stubs for skeleton =====
void RfidReader::begin() {}
bool RfidReader::poll(RfidEvent& out_event) {
  (void)out_event;
  return false;
}

void ServoController::begin() {}
void ServoController::setSwitch(uint8_t switch_id, SwitchPosition position) {
  (void)switch_id;
  (void)position;
}

void SerialTransport::begin(unsigned long baud) { Serial.begin(baud); }
void SerialTransport::sendLine(const String& line) { Serial.println(line); }
bool SerialTransport::readLine(String& out_line) {
  if (!Serial.available()) return false;
  out_line = Serial.readStringUntil('\n');
  out_line.trim();
  return out_line.length() > 0;
}

bool CommandHandler::handleLine(const String& line) {
  // TODO: parse JSON command and map to switch action
  if (line.startsWith("CMD SWITCH1 STRAIGHT")) {
    servo_.setSwitch(1, SwitchPosition::STRAIGHT);
    return true;
  }
  if (line.startsWith("CMD SWITCH1 DIVERGE")) {
    servo_.setSwitch(1, SwitchPosition::DIVERGE);
    return true;
  }
  return false;
}

void setup() {
  transport.begin(kBaud);
  rfid.begin();
  servo.begin();
  transport.sendLine("BOOT node=node-1 platform=pico2w transport=usb-serial");
}

void loop() {
  const uint32_t now = millis();

  if (now - last_heartbeat_ms >= kHeartbeatEveryMs) {
    last_heartbeat_ms = now;
    transport.sendLine("HEARTBEAT node=node-1");
  }

  RfidEvent event;
  if (rfid.poll(event)) {
    const bool is_duplicate = (event.uid == last_uid) && ((now - last_uid_ms) < kDuplicateWindowMs);
    if (!is_duplicate) {
      last_uid = event.uid;
      last_uid_ms = now;
      transport.sendLine("RFID uid=" + event.uid);
    }
  }

  String line;
  if (transport.readLine(line)) {
    const bool ok = cmd.handleLine(line);
    transport.sendLine(ok ? "ACK ok" : "ACK unknown_command");
  }
}
